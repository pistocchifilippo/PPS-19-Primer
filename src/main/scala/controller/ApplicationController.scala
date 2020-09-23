package controller

import cats.effect.IO
import controller.simulator.Simulator
import model.output.Output._
import model.output.Output

object ApplicationController {

  /** This function executes a simulation until it's expiredThis function executes a simulation until it's expired
   *
   * @return The output of the simulation
   */
  def execute: Simulator => IO[Output] = sim => sim.foldRight(Output())((sim, out) => log(out)(sim.executedStep - 1, sim.environment))

}
