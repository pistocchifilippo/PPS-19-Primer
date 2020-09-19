package controller

import controller.simulator.Simulator
import model.output.Output._

case class ApplicationController() {

  /** This function executes a simulation until it's expired
   *
   * @param simulator that will be executed
   * @return The output of the simulation
   */
  def execute(simulator: Simulator)(output: Output): Output =
    if (simulator.hasNext) {
      val sim = simulator.next()
      execute(sim)(log(output)(simulator.executedStep, sim.environment))
    } else output

}
