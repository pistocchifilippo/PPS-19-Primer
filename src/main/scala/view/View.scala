package view

import java.awt.Dimension
import java.io.IOException

import helpers.Configurations.{SIMULATOR_HEIGHT, SIMULATOR_TITLE, SIMULATOR_WIDTH}
import model.Environment
import scalaz.ioeffect.IO
import scalaz.ioeffect.console.{getStrLn, putStrLn}
import helpers.Strategies._
import javax.swing.JFrame
import model.output.Output.Output

trait SimulationView{
  def print: Output => IO[IOException, Unit]
  def update(performUpdate: () => Unit) { performUpdate() }
}

case class View(override val print: Output => IO[IOException, Unit])
               (val frame: Option[JFrame]) extends SimulationView {
}

object SimulationView {


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
      case (mode, file, nDays, nBodies, nFood) if checkParameters(mode, file, nDays, nBodies, nFood)
        => (mode, file) match {
        case ("1", "y") => Option(Parameters(View(printFile)(getFrame(false)), nDays, nBodies, nFood))
        case ("1", "n") => Option(Parameters(View(printCLI)(getFrame(false)), nDays, nBodies, nFood))
        case ("2", "y") => Option(Parameters(View(printFile)(getFrame(true)) , nDays, nBodies, nFood))
        case ("2", "n") => Option(Parameters(View(printCLI)(getFrame(true)), nDays, nBodies, nFood))
        }
      case _ => Option.empty
    }

  }


  def update(sView: SimulationView, environment: Environment) {
    sView match {
      case view: View => view.update(updateJFrame(environment, view.frame))
      case _ => {}
    }
  }

  def checkParameters(mode: String, file: String, nDays: Int, nCreatures: Int, nFood: Int): Boolean ={
    ((mode equals "1" )|| (mode equals "2")) &&
      ((file equals "y") || (file equals "n")) &&
      (nDays >= 0) && (nCreatures > 0) && (nFood > 0)
  }

  def buildFrame() = new JFrame(SIMULATOR_TITLE){
    setDefaultCloseOperation(3)
    setSize(new Dimension(SIMULATOR_WIDTH, SIMULATOR_HEIGHT))
    setLocationRelativeTo(null)
    setVisible(true)
  }


}

