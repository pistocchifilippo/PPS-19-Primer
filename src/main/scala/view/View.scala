package view

import java.awt.Dimension
import java.io.IOException

import helpers.Configurations.{SIMULATOR_HEIGHT, SIMULATOR_TITLE, SIMULATOR_WIDTH}
import model.Environment
import scalaz.ioeffect.IO
import scalaz.ioeffect.console.{getStrLn, putStrLn}
import helpers.Strategies._
import javax.swing.JFrame

trait SimulationView{
  def print: String => Unit
  def update: (Environment, Option[JFrame]) => Option[Visualizer]
  def frame: Option[JFrame]
}

case class View(override val print: String => Unit)
               (override val update: (Environment, Option[JFrame]) => Option[Visualizer])
               (override val frame: Option[JFrame]) extends SimulationView {
}

object View {


  def buildWithIO : IO[IOException, Option[Parameters]] = {
    for {
      _ <- putStrLn("Welcome to natural selection simulator!!!")
      _ <- putStrLn("Scegli la modalità di esecuzione")
      _ <- putStrLn("1. Simulation mode")
      _ <- putStrLn("2. Test mode")
      simMode <- getStrLn
      _ <- putStrLn("Stampare le statistiche su file? y/n")
      outputFile <- getStrLn
      _ <- putStrLn("Durata simulazione (in giorni)")
      days <- getStrLn
      _ <- putStrLn("Numero corpi")
      bodies <- getStrLn
      _ <- putStrLn("Numero unità cibo")
      food <- getStrLn
    } yield (simMode, outputFile, days.toInt, bodies.toInt, food.toInt) match {
      case (mode, file, nDays, nBodies, nFood) if nDays > 0 && nBodies > 0 && nFood > 0
        => (mode, file) match {
        case ("1", "y") => Option(Parameters(View(printFile)(update)(getFrame(false)), nDays, nBodies, nFood))
        case ("1", "n") => Option(Parameters(View(printCLI)(update)(getFrame(false)), nDays, nBodies, nFood))
        case ("2", "y") => Option(Parameters(View(printFile)(update)(getFrame(true)) , nDays, nBodies, nFood))
        case ("2", "n") => Option(Parameters(View(printCLI)(update)(getFrame(true)), nDays, nBodies, nFood))
        }
      case _ => Option.empty
    }

  }

  def buildFrame() = new JFrame(SIMULATOR_TITLE){
    setDefaultCloseOperation(3)
    setSize(new Dimension(SIMULATOR_WIDTH, SIMULATOR_HEIGHT))
    setLocationRelativeTo(null)
    setVisible(true)
  }


}
