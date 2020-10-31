package model

import cats.effect.IO
import helpers.Configurations.{BOUNDARIES, CREATURES_ENERGY}
import helpers.Strategies.{collidingCreatures, collidingFood, randomGoal}
import model.creature.Gene
import model.creature.Gene.GeneMutation
import model.creature.movement.EnvironmentCreature.{EnvironmentCreature, ReproducingCreature, StarvingCreature}
import model.environment.Environment.Environment
import model.environment.Position.Position
import model.environment.{Blob, Environment, Food}

/** This module contains model functionalities that exploit the [[IO]] monad
 * This functions can be integrated into the simulator next routine
 * */
object Model {

  type FoodCreatureCollision = (EnvironmentCreature, Food)

  /** Moves of one step each [[EnvironmentCreature]] into the collection
   *
   * @param creatures         that will be moved
   * @param energyConsumption that will be decreased to creature energy
   * @return the new set of moved creatures
   */
  def moveCreatures(creatures: Traversable[EnvironmentCreature])(implicit energyConsumption: (Double, Double) => Double): IO[Traversable[EnvironmentCreature]] =
    IO pure {
      creatures map (_.move(energyConsumption))
    }

  /** Compute the [[FoodCreatureCollision]] and puts into a [[Traversable]]
   *
   * @param creatures that have been moved
   * @param food      in the environment
   * @return A tuple containing creatures and food that collides
   */
  def collisions(creatures: Traversable[EnvironmentCreature])(food: Traversable[Food]): IO[Traversable[FoodCreatureCollision]] = IO pure {
    for {
      c <- creatures
      f <- food
      if Blob.collide(c)(f) && {
        c match {
          case _: ReproducingCreature => false
          case _ => true
        }
      }
    } yield (c, f)
  }

  /** Makes a new [[Environment]] (of the same day) applying changes due to creature movement and collisions
   *
   * @param movedCreatures creature moved
   * @param foodCollection the old food set
   * @param coll           the collisions between [[EnvironmentCreature]] and [[Food]]
   * @return the new environment
   */
  def makeNewEnvironment(movedCreatures: Traversable[EnvironmentCreature])(foodCollection: Traversable[Food])(coll: Traversable[FoodCreatureCollision]): IO[Environment] = for {
    c <- updateCreatureCollection(movedCreatures)(collidingCreatures(coll))
    f <- updateFoodCollection(foodCollection)(collidingFood(coll))
  } yield Environment(BOUNDARIES, f, c)

  private def updateCreatureCollection(creatureCollection: Traversable[EnvironmentCreature])(toFeed: List[EnvironmentCreature]): IO[Traversable[EnvironmentCreature]] = IO pure {
    creatureCollection collect {
      case cr if toFeed contains cr => cr.feed()
      case cr => cr
    }
  }

  private def updateFoodCollection(foodCollection: Traversable[Food])(toRemove: List[Food]): IO[Traversable[Food]] = IO pure {
    foodCollection filter (!toRemove.contains(_))
  }

  /** Build the new set of [[EnvironmentCreature]] from the creature collection of the day before
   *
   * @param creatures    old creature set
   * @param pos          a position generator for replace creatures
   * @param geneMutation how [[Gene]] changes
   * @return The [[EnvironmentCreature]] collection of the new day
   */
  def evolutionSet(creatures: Traversable[EnvironmentCreature])(pos: () => Position)(geneMutation: GeneMutation): IO[Traversable[EnvironmentCreature]] = IO pure {
    creatures flatMap {
      _ match {
        case _: StarvingCreature => Traversable.empty
        case c: EnvironmentCreature => Traversable(StarvingCreature(pos(), c.speed, CREATURES_ENERGY, c.radius, randomGoal)) ++ c.reproduce(geneMutation)(pos)
      }
    }
  }

}
