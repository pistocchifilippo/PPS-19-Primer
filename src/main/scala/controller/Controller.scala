package controller

import controller.simulator.{DaySimulator, Simulator}
import model.{Environment, Position}
import view.{View, Visualizer}
import helpers.Configurations.{BOUNDARIES, CREATURES_ENERGY, CREATURES_RADIUS, CREATURES_SPEED, FOOD_RADIUS, SIMULATOR_HEIGHT, SIMULATOR_WIDTH}
import helpers.Strategies._
import model.Blob._
import helpers.Configurations._
import model.output.Output
import model.output.Output._

//case class Controller(view: View)(nDays: Int, nCreature: Int, nFood: Int){
case class Controller(){

  var output : Output = Output()

  //def execute(simulator: Simulator): Unit = {
  def execute(simulator: Simulator): Output = {

    if(simulator.hasNext) {
      val nextDay = simulator.next()
      output = log(output)(1, nextDay.environment)
      execute(nextDay)
    }
    output
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

  }
}