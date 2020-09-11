package view

import java.io.{File, FileWriter, IOException}
import helpers.Configurations._
import model.Environment
import scalaz.ioeffect.IO
import scalaz.ioeffect.console.{getStrLn, putStrLn}

abstract class View {
  def print(stats: String): IO[IOException, Unit]
}

 class CLIView() extends View {
  override def print(stats: String): IO[IOException, Unit] = putStrLn(stats) //print on console
}

 class FileView() extends View {

  override def print(stats: String): IO[IOException, Unit] = for {
    out <- IO.now(new File(OUTPUT_PATH))
    fw <- IO.now(new FileWriter(out))
    _ <- IO.now(fw.write(stats))
    _ <- IO.now(fw.close())
  } yield ()
  // --> print on file
}

trait GUI extends View {
  def update(environment: Environment): Unit = for {
    panel <- IO.now(new Visualizer(environment))
  } yield()
  //--> same for both GUICli and GUIFile
}

case class GUICliView() extends CLIView() with GUI

case class GUIFileView() extends FileView() with GUI


object View {

  def collectSimulationParameters : IO[IOException, (Int, Int, Int)] = for {
    _ <- putStrLn("Durata simulazione (in giorni)")
    days <- getStrLn
    _ <- putStrLn("Numero corpi")
    bodies <- getStrLn
    _ <- putStrLn("Numero unità cibo")
    food <- getStrLn
  } yield (days.toInt, bodies.toInt, food.toInt)

  def buildWithIO : IO[IOException, View] = {
    for {
      _ <- putStrLn("Welcome to natural selection simulator!!!")
      _ <- putStrLn("Scegli la modalità di esecuzione")
      _ <- putStrLn("1. Simulation mode")
      _ <- putStrLn("2. Test mode")
      t <- getStrLn
      _ <- putStrLn("Stampare le statistiche su file? y/n")
      file <- getStrLn
    } yield (t, file) match {
      case ("1", "y") => new FileView()
      case ("1", "n") => new CLIView()
      case ("2", "y") => new GUIFileView()
      case ("2", "n") => new GUICliView()
    }
  }

}
