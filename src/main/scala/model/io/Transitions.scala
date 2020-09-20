package model.io

import cats.effect.IO
import helpers.Configurations.CREATURES_ENERGY
import helpers.Strategies.randomGoal
import model.{Blob, Environment, Food, Position}
import model.creature.movement.EnvironmentCreature.EnvironmentCreature
import model.creature.movement.StarvingCreature
import helpers.Configurations._

object Transitions {

  type Collision = (Blob, Blob)

  def moveCreatures(creatures: Traversable[EnvironmentCreature])(implicit energyConsumption: (Double,Double) => Double): IO[Traversable[EnvironmentCreature]] = IO.pure{creatures map (_.move(energyConsumption))}
  def collisions(c: Traversable[EnvironmentCreature])(f: Traversable[Food]): IO[Traversable[Collision]] = ???
  def updateEnvironment(env: Environment)(coll: Traversable[Option[Collision]]): IO[Environment] = for {

    c <- IO {coll.collect{case Some(c) => c._1}.toList}
    f <- IO {coll.collect{case Some(f) => f._1}.toList}

    // the new food set
    newF <- IO {env.food filter (!f.contains(_))}

    // the new creature set
    newC <- IO {env.creatures collect {
      case cr if c.contains(cr) => cr.feed()
      case cr => cr
    }}

  } yield Environment(BOUNDARIES, newF, newC)

  def evolutionSet(creatures: Traversable[EnvironmentCreature])(pos: () => Position)(sizeMutation: Double => Double)(speedMutation: Double => Double): IO[Traversable[EnvironmentCreature]] = IO.pure{creatures flatMap {
    _ match {
      case StarvingCreature(_,_,_,_,_) => Traversable.empty
      case c: EnvironmentCreature => Traversable(StarvingCreature(pos(), c.speed, CREATURES_ENERGY, c.radius, randomGoal)) ++ c.reproduce(sizeMutation)(speedMutation)(pos)
    }
  }}

}
