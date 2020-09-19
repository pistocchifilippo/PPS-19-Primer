package helpers.io

import cats.effect.IO
import controller.simulator.Simulator
import model.Environment
import model.output.Output.Output

object IoConversion {

  implicit def outputToIo(output: Output): IO[Output] = IO.pure(output)
  implicit def environmentToIo(environment: Environment): IO[Environment] = IO.pure(environment)
  implicit def simulatorToIo(simulator: Simulator): IO[Simulator] = IO.pure(simulator)

}
