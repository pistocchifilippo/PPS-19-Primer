package helpers.io

import cats.effect.IO
import controller.simulator.Simulator
import model.Environment
import model.creature.movement.EnvironmentCreature.EnvironmentCreature

object IoConversion {

  implicit def creaturesToIo(creatures: Traversable[_]): IO[Traversable[_]] = IO.pure(creatures)
  implicit def environmentToIo(environment: Environment): IO[Environment] = IO.pure(environment)
  implicit def simulatorToIo(simulator: Simulator): IO[Simulator] = IO.pure(simulator)

}
