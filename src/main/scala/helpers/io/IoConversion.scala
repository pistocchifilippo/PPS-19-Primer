package helpers.io

import cats.effect.IO
import model.Environment
import model.creature.movement.EnvironmentCreature.EnvironmentCreature
import model.output.Output.Output
import view.Visualizer

object IoConversion {

  implicit def creatureToIo(c: EnvironmentCreature): IO[EnvironmentCreature] = IO pure c
  implicit def outputToIo(out: Output): IO[Output] = IO pure out
  implicit def environmentToIo(env: Environment): IO[Environment] = IO pure env
  implicit def visualizerToIo(visualizer: Visualizer): IO[Visualizer] = IO.pure(visualizer)
  implicit def unitToIo(fun: Unit): IO[Unit] = IO.pure { () => fun }

}
