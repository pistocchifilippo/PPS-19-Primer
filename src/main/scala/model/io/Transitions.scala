package model.io

import cats.effect.IO
import helpers.Configurations.CREATURES_ENERGY
import helpers.Strategies.randomGoal
import model.{Blob, Environment, Food, Position}
import model.creature.movement.EnvironmentCreature.EnvironmentCreature
import model.creature.movement.{ReproducingCreature, StarvingCreature}
import helpers.Configurations._

object Transitions {

  def moveCreatures(creatures: Traversable[EnvironmentCreature])(implicit energyConsumption: (Double,Double) => Double): IO[Traversable[EnvironmentCreature]] = IO pure {creatures map (_.move)}

  def collisions(creatures: Traversable[EnvironmentCreature])(food: Traversable[Food]): IO[Traversable[(EnvironmentCreature, Food)]] = IO pure {
    for {
      c <- creatures
      f <- food
      if Blob.collide(c)(f) && {c match {
        case ReproducingCreature(_, _, _, _, _) => false
        case _ => true}
      }
    } yield (c, f)
  }

  def makeNewEnvironment(newCreatures: Traversable[EnvironmentCreature])(food: Traversable[Food])(coll: Traversable[(EnvironmentCreature, Food)]): IO[Environment] = for {

    c <- IO {coll.map{_._1}.toList}
    f <- IO {coll.map{_._2}.toList}

    // the new food set
    newF <- IO {food filter (!f.contains(_))}

    // the new creature set
    newC <- IO {newCreatures collect {
      case cr if c.contains(cr) => cr.feed()
      case cr => cr
    }}

  } yield Environment(BOUNDARIES, newF, newC)

  def evolutionSet(creatures: Traversable[EnvironmentCreature])(pos: () => Position)(sizeMutation: Double => Double)(speedMutation: Double => Double): IO[Traversable[EnvironmentCreature]] = IO pure {
    creatures flatMap {
      _ match {
        case StarvingCreature(_,_,_,_,_) => Traversable.empty
        case c: EnvironmentCreature => Traversable(StarvingCreature(pos(), c.speed, CREATURES_ENERGY, c.radius, randomGoal)) ++ c.reproduce(sizeMutation)(speedMutation)(pos)
      }
    }
  }


}
