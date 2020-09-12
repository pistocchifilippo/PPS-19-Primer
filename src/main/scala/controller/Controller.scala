package controller

import controller.simulator.DaySimulator
import model.Environment
import view.{View, Visualizer}
import helpers.Configurations.{BOUNDARIES, CREATURES_ENERGY, CREATURES_RADIUS, CREATURES_SPEED, FOOD_RADIUS}
import helpers.Strategies._
import model.entity.{Creature, Food}

case class Controller(view: View)(nDays: Int, nCreature: Int, nFood: Int){

  def execute(): Unit = { //BOZZA
    //creo environment

    val environment = Environment(
      BOUNDARIES,
      Food(nFood, FOOD_RADIUS)(randomPosition(BOUNDARIES)),
      Creature.makeSet(nCreature, CREATURES_RADIUS, CREATURES_ENERGY, CREATURES_SPEED)(randomPosition(BOUNDARIES))
    )



    // creo simulator

    val simulator = DaySimulator(
      nFood,
      nDays,
      environment)
     // --> aggiungere view come parametro?
     // --> rimuovere nFood come parametro? recuperabile da environment.food.size anche all'interno

    view.frame match {
      case Some(jFrame) => {
        jFrame.setVisible(true)
        jFrame.getContentPane.removeAll()
        jFrame.getContentPane.add(Visualizer(environment))
      }
      case None => {}
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


//case class SimulationModeController(override val nDays: Int, override val nBodies: Int, override val nFood: Int) extends Controller {
//
//  override def execute: IO[IOException, Unit] = for {
//    bounds <- IO.now(Boundaries(TOP_LEFT, BOTTOM_RIGHT))
//    bodies <- IO.now(Creature.makeSet(nBodies, BODIES_RADIUS, BODIES_ENERGY, BODIES_SPEED)(randomPosition(bounds)))
//    food <- IO.now(Food(nFood, BODIES_RADIUS)(randomPosition(bounds)))
//    env <- IO.now(Environment(bounds, food, bodies))
//    controller <- IO.now(AppController(nDays, env))
//    stats <- IO.now(controller.execute)
//
//    //otuput on file/console
//
//  } yield ()
//
//}
//
//
//case class TestModeController(override val nDays: Int, override val nBodies: Int, override val nFood: Int) extends Controller{
//  override def execute: IO[IOException, Unit] = for {
//
//    //build GUI
//
//    bounds <- IO.now(Boundaries(TOP_LEFT, BOTTOM_RIGHT))
//    bodies <- IO.now(Creature.makeSet(nBodies, BODIES_RADIUS, BODIES_ENERGY, BODIES_SPEED)(randomPosition(bounds)))
//    food <- IO.now(Food(nFood, BODIES_RADIUS)(randomPosition(bounds)))
//    env <- IO.now(Environment(bounds, food, bodies))
//    controller <- IO.now(AppController(nDays, env))
//    stats <- IO.now(controller.execute)
//
//    //output on file/GUI/console
//
//  } yield ()
//}