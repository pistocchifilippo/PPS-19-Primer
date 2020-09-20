package controller

import cats.effect.IO
import controller.simulator.Simulator
import model.output.Output._
import helpers.io.IoConversion._

case class ApplicationController() {

  /** This function executes a simulation until it's expired
   *
   * @param simulator that will be executed
   * @return The output of the simulation
   */
  def execute(simulator: Simulator)(output: Output): IO[Output] =
    if (simulator.hasNext) for {
      sim <- simulator.next()
      step <- execute(sim)(log(output)(simulator.executedStep, simulator.environment))
    } yield step
    else IO{output}
//    if (simulator.hasNext) {
//      val sim = simulator.next()
//      execute(sim)(log(output)(simulator.executedStep, sim.environment))
//    } else output

}
