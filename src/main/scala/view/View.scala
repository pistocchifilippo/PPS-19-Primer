package view

import java.awt.Dimension
import java.io.IOException

import helpers.Configurations.{BOUNDARIES, SIMULATOR_HEIGHT, SIMULATOR_TITLE, SIMULATOR_WIDTH}
import model.Environment
import scalaz.ioeffect.IO
import scalaz.ioeffect.console.{getStrLn, putStrLn}
import helpers.Strategies._
import javax.swing.JFrame

trait SimulationView{
  def print: String => Unit
  def update: (Environment, JFrame) => Unit
  def frame: Option[JFrame]
}

case class View(override val print: String => Unit)
               (override val update: (Environment, JFrame) => Unit)
               (override val frame: Option[JFrame]) extends SimulationView {
}

object View {

  def collectSimulationParameters : IO[IOException, (Int, Int, Int)] = for {
    _ <- putStrLn("Durata simulazione (in giorni)")
    days <- getStrLn
    _ <- putStrLn("Numero corpi")
    bodies <- getStrLn
    _ <- putStrLn("Numero unità cibo")
    food <- getStrLn
  } yield (days.toInt, bodies.toInt, food.toInt)

  def buildWithIO : IO[IOException, Option[View]] = {
    for {
      _ <- putStrLn("Welcome to natural selection simulator!!!")
      _ <- putStrLn("Scegli la modalità di esecuzione")
      _ <- putStrLn("1. Simulation mode")
      _ <- putStrLn("2. Test mode")
      t <- getStrLn
      _ <- putStrLn("Stampare le statistiche su file? y/n")
      file <- getStrLn
    } yield (t, file) match {
      case ("1", "y") => Option(View(printFile)(none)(getFrame(false)))
      case ("1", "n") => Option(View(printCLI)(none)(getFrame(false)))
      case ("2", "y") => Option(View(printFile)(update)(getFrame(true)))
      case ("2", "n") => Option(View(printCLI)(update)(getFrame(true)))
      case _ => Option.empty
    }
  }

  def buildFrame() = new JFrame(SIMULATOR_TITLE){
    setDefaultCloseOperation(3)
    setSize(new Dimension(SIMULATOR_WIDTH, SIMULATOR_HEIGHT))
    setLocationRelativeTo(null)
    //setVisible(true)
    //getContentPane.add(Visualizer(environment))
  }


}
