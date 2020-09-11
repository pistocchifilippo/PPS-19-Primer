package controller

import java.io.IOException

import controller.simulator.{DaySimulator, Simulator}
import helpers.Configurations._
import helpers.Strategies._
import model._
import model.entity._
import model.output.Output
import scalaz.ioeffect.IO
import scalaz.ioeffect.console._
import view.GUI

sealed trait Controller {
  def execute(sim: DaySimulator): Output
}

case class TMController(gui: GUI) extends Controller {
  override def execute(sim: DaySimulator): Output = ???
}

case class SMController(gui: GUI) extends Controller {
  override def execute(sim: DaySimulator): Output = ???
}







object Controller {

//  def buildWithIO: Controller = for {
//    _ <- putStrLn("Welcome to natural selection simulator!!!")
//    _ <- putStrLn("Scegli la modalità di esecuzione")
//    _ <- putStrLn("1. Simulation mode")
//    _ <- putStrLn("2. Test mode")
//    mode <- getStrLn
//    _ <- putStrLn("Stampare le statistiche su file? y/n")
//    outputMode <- getStrLn
//    _ <- putStrLn("Durata simulazione (in giorni)")
//    days <- getStrLn
//    _ <- putStrLn("Numero corpi")
//    bodies <- getStrLn
//    _ <- putStrLn("Numero unità cibo")
//    food <- getStrLn
//  } yield ()

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