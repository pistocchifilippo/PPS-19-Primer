package controller

import controller.simulator.Simulator
import model.output.Output
import model.output.Output._

case class ApplicationController() {

  var output: Output = Output()

  def execute(simulator: Simulator): Output = {

    if (simulator.hasNext) {
      val nextDay = simulator.next()
      output = log(output)(simulator.executedStep, nextDay.environment)
      execute(nextDay)
    } else output
  }
}

/*
    view.update(environment, view.frame) match {
      case Some(visualizer) => {
        visualizer.validate()
        visualizer.repaint()
        view.frame.get.setSize(SIMULATOR_WIDTH+1, SIMULATOR_HEIGHT+1)
      }
      case _ =>
    }
 */


    // display view
    /*
    view.frame match {
      case Some(value) => value.getContentPane.add(Visualizer(environment))
      case _ => {}
    }
     */


