package view

import java.io.IOException

import scalaz.ioeffect.IO
import scalaz.ioeffect.console.{getStrLn, putStrLn}

abstract class View {
  def print(): Unit
}

case class CLIView() extends View {
  override def print(): Unit = ??? // --> print on console
}

case class FileView() extends View {
  override def print(): Unit = ??? // --> print on file
}
trait GUI extends View {

  def update: Unit
}

case class GUICliView() extends CLIView with GUI{
  override def update: Unit = ???
}
case class GUIFileView() extends FileView with GUI{
  override def update: Unit = ???
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

  def buildWithIO : IO[IOException, View] = for {
    _ <- putStrLn("Welcome to natural selection simulator!!!")
    _ <- putStrLn("Scegli la modalità di esecuzione")
    _ <- putStrLn("1. Simulation mode")
    _ <- putStrLn("2. Test mode")
    t <- getStrLn
    _ <- putStrLn("Stampare le statistiche su file? y/n")
    file <- getStrLn

  } yield (t, file) match {
    case ("1", "y") => FileView
    case ("1", "n") => CLIView
    case ("2", "y") => GUIFileView
    case ("2", "n") => GUICliView
  }
}