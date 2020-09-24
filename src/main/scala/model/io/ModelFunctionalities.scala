package model.io

import cats.effect.IO
import helpers.Configurations.CREATURES_ENERGY
import helpers.Strategies.randomGoal
import model.{Blob, Environment, Food, Position}
import model.creature.movement.EnvironmentCreature.EnvironmentCreature
import model.creature.movement.{ReproducingCreature, StarvingCreature}
import helpers.Configurations._
import helpers.Strategies._

/** This module contains model functionalities that exploit the IO monad
 *  This functions can be integrated into the simulator next routine
 * */
object ModelFunctionalities {

  /** Moves of one step each creature into the collection
   *
   * @param creatures that will be moved
   * @param energyConsumption that will be decreased to creature energy
   * @return the new set of moved creatures
   */
  def moveCreatures(creatures: Traversable[EnvironmentCreature])(implicit energyConsumption: (Double,Double) => Double): IO[Traversable[EnvironmentCreature]] = IO pure {creatures map (_.move)}

  type FoodCreatureCollision = (EnvironmentCreature, Food)

  /** Compute the collisions
   *
   * @param creatures that have been moved
   * @param food in the environment
   * @return A tuple containing creatures and food that collides
   */
  def collisions(creatures: Traversable[EnvironmentCreature])(food: Traversable[Food]): IO[Traversable[FoodCreatureCollision]] = IO pure {
    for {
      c <- creatures
      f <- food
      if Blob.collide(c)(f) && {c match {
        case ReproducingCreature(_, _, _, _, _) => false
        case _ => true}
      }
    } yield (c, f)
  }

  private def updateCreatureCollection(creatureCollection: Traversable[EnvironmentCreature])(toFeed: List[EnvironmentCreature]): IO[Traversable[EnvironmentCreature]] = IO pure {creatureCollection collect {
    case cr if {toFeed contains cr} => cr.feed()
    case cr => cr
  }}
  private def updateFoodCollection(foodCollection: Traversable[Food])(toRemove: List[Food]): IO[Traversable[Food]] = IO pure {foodCollection filter (!toRemove.contains(_))}

  /** Makes a new environment (of the same day) applying changes due to creature movement and collisions
   *
   * @param movedCreatures
   * @param foodCollection
   * @param coll
   * @return the new environment
   */
  def makeNewEnvironment(movedCreatures: Traversable[EnvironmentCreature])(foodCollection: Traversable[Food])(coll: Traversable[FoodCreatureCollision]): IO[Environment] = for {
    c <- updateCreatureCollection(movedCreatures)(collidingCreatures(coll))
    f <- updateFoodCollection(foodCollection)(collidingFood(coll))
  } yield Environment(BOUNDARIES, f, c)

  /** Build the new set of creatures from the creature collection of the day before
   *
   * @param creatures
   * @param pos
   * @param sizeMutation
   * @param speedMutation
   * @return The creature collection of the new day
   */
  def evolutionSet(creatures: Traversable[EnvironmentCreature])(pos: () => Position)(sizeMutation: Double => Double)(speedMutation: Double => Double): IO[Traversable[EnvironmentCreature]] = IO pure {
    creatures flatMap {
      _ match {
        case StarvingCreature(_,_,_,_,_) => Traversable.empty
        case c: EnvironmentCreature => Traversable(StarvingCreature(pos(), c.speed, CREATURES_ENERGY, c.radius, randomGoal)) ++ c.reproduce(sizeMutation)(speedMutation)(pos)
      }
    }
  }

}
