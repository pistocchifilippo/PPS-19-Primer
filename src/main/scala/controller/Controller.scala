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
  def view: View
  def execute(sim: DaySimulator): Output
}

case class TMController(view: GUI) extends Controller {
  override def execute(sim: DaySimulator): Output = ???
}

case class SMController(view: View) extends Controller {
  override def execute(sim: DaySimulator): Output = ???
}


object Controller {

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