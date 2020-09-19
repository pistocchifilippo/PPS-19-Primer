package controller

import controller.simulator.Simulator
import model.output.Output
import model.output.Output._

case class ApplicationController() {

  var output: Output = Output()

  /** This function executes a simulation until it's expired
   *
   * @param simulator that will be executed
   * @return The output of the simulation
   */
  def execute(simulator: Simulator): Output =
    if (simulator.hasNext) {
      val nextDay = simulator.next()
      output = log(output)(simulator.executedStep, nextDay.environment)
      execute(nextDay)
    } else output

}
