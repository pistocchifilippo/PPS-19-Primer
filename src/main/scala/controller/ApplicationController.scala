package controller

import cats.effect.IO
import controller.simulator.Simulator
import model.output.Output._
import helpers.io.IoConversion._
import model.output.Output

case class ApplicationController() {

  /** This function executes a simulation until it's expired
   *
   * @param simulator that will be executed
   * @return The output of the simulation
   */
  def execute(simulator: Simulator)(output: Output): IO[Output] = simulator.foldRight(Output())((sim, out) => log(out)(sim.executedStep, sim.environment))
//    if (simulator.hasNext) for {
//      sim <- simulator.next()
//      step <- execute(sim)(log(output)(simulator.executedStep, simulator.environment))
//    } yield step
//    else IO{output}

}
