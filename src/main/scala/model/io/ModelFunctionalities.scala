package model.io

import cats.effect.IO
import controller.simulator.{DaySimulator, Simulator}
import helpers.Configurations.CREATURES_ENERGY
import helpers.Strategies.randomGoal
import model.{Blob, Environment, Food, Position}
import model.creature.movement.EnvironmentCreature.EnvironmentCreature
import model.creature.movement.{ReproducingCreature, StarvingCreature}
import helpers.Configurations._
import helpers.Strategies._
import view.SimulationParameters

object ModelFunctionalities {

  def moveCreatures(creatures: Traversable[EnvironmentCreature])(implicit energyConsumption: (Double,Double) => Double): IO[Traversable[EnvironmentCreature]] = IO pure {creatures map (_.move)}

  type FoodCreatureCollision = (EnvironmentCreature, Food)
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

  def makeNewEnvironment(movedCreatures: Traversable[EnvironmentCreature])(foodCollection: Traversable[Food])(coll: Traversable[FoodCreatureCollision]): IO[Environment] = for {
    c <- updateCreatureCollection(movedCreatures)(collidingCreatures(coll))
    f <- updateFoodCollection(foodCollection)(collidingFood(coll))
  } yield Environment(BOUNDARIES, f, c)

  def evolutionSet(creatures: Traversable[EnvironmentCreature])(pos: () => Position)(sizeMutation: Double => Double)(speedMutation: Double => Double): IO[Traversable[EnvironmentCreature]] = IO pure {
    creatures flatMap {
      _ match {
        case StarvingCreature(_,_,_,_,_) => Traversable.empty
        case c: EnvironmentCreature => Traversable(StarvingCreature(pos(), c.speed, CREATURES_ENERGY, c.radius, randomGoal)) ++ c.reproduce(sizeMutation)(speedMutation)(pos)
      }
    }
  }

}
