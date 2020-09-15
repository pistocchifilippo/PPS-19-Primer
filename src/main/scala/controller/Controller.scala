package controller

import controller.simulator.DaySimulator
import model.{Environment, Position}
import view.{View, Visualizer}
import helpers.Configurations.{BOUNDARIES, CREATURES_ENERGY, CREATURES_RADIUS, CREATURES_SPEED, FOOD_RADIUS, SIMULATOR_HEIGHT, SIMULATOR_WIDTH}
import helpers.Strategies._
import model.Blob._
import helpers.Configurations._

case class Controller(view: View)(nDays: Int, nCreature: Int, nFood: Int){

  def execute(): Unit = {
    //BOZZA

    //creo environment
    val environment = Environment(
      BOUNDARIES,
      makeBoundedFoodCollection(nFood),
      makeOnBoundsCreaturesCollection(nCreature)
    )

    // creo simulator
    val simulator = DaySimulator(
      nFood,
      nDays,
      environment,
      view)
     // --> rimuovere nFood come parametro? recuperabile da environment.food.size anche all'interno

    view.update(environment, view.frame) match {
      case Some(visualizer) => {
        visualizer.validate()
        visualizer.repaint()
        view.frame.get.setSize(SIMULATOR_WIDTH+1, SIMULATOR_HEIGHT+1)
      }
    }

    // display view
    /*
    view.frame match {
      case Some(value) => value.getContentPane.add(Visualizer(environment))
      case _ => {}
    }
     */

    // run simulator -> update view each step
    /*
    ...
    */

  }
}