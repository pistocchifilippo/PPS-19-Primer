package controller

import java.io.IOException

import controller.simulator.{DaySimulator, DayStepSimulator, Simulator}
import helpers.Configurations._
import helpers.Strategies._
import model._
import model.entity._
import model.output.Output
import scalaz.ioeffect.IO
import scalaz.ioeffect.console._
import view.{CLIView, FileView, GUI, GUICliView, GUIFileView, View}

sealed trait Controller {
  def execute(sim: DaySimulator): Output
}

case class TMController(gui: GUI) extends Controller {
  override def execute(sim: DaySimulator): Output = ???
}

case class SMController(view: View) extends Controller {
  override def execute(sim: DaySimulator): Output = ???
}


trait GUIController extends Simulator {

  private[this] var gui = "GUI"

  abstract override def next(): Simulator = {
    val newSim = super.next()
    // gui.update(newSim.environment)
    newSim
  }

}

class SimulationView(environment: Environment) extends DayStepSimulator(environment) with GUIController




object Controller {

  //def makeIOController(view: View, nDays: Int, nFood: Int, nCreatures: Int): IO[IOException, Controller] = view match {
    //case (FileView() || CLIView) => SMController(view)
    //case (GUIFileView() || GUICliView) => SMController(view)
  //}
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