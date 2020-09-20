package model.io

import cats.effect.IO
import controller.simulator.{DayStepSimulator, Simulator}
import model.{Blob, Environment, Food, Position}
import model.creature.movement.EnvironmentCreature.EnvironmentCreature

object Transitions {

  type Collision = (Blob, Blob)

  def moveCreatures(creatures: Traversable[EnvironmentCreature])(implicit energyConsumption: (Double,Double) => Double): IO[Traversable[EnvironmentCreature]] = IO.pure{creatures map (_.move(energyConsumption))}
  def collisions(c: Traversable[EnvironmentCreature])(f: Traversable[Food]): IO[Traversable[Collision]] = ???
  def updateEnvironment(old: Environment)(coll: Traversable[Collision]): IO[Environment] = ???

  def evolutionSet(creatures: Traversable[EnvironmentCreature])(pos: () => Position)(sizeMutation: Double => Double)(speedMutation: Double => Double): IO[Traversable[EnvironmentCreature]] = ???

}
