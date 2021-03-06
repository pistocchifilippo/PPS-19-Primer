package view

import cats.effect.IO
import helpers.Configurations._
import helpers.Strategies._
import model.output.Output.Output
import view.graphic.GraphicalComponent.GraphicalEnvironment
import view.graphic._
import view.utils.SimulationParameters
import view.utils.ViewUtils._

/** This is a top-level module containing view functionalities */
object View {

  /** Retrieves all the parameters.
   *
   * @return a [[SimulationParameters]] element, casted in [[IO]], to be used in a for-comprehension statement. */
  def collectParameters: IO[SimulationParameters] = for {
    _ <- putStrLn(WELCOME)
    mode <- scheduleGet(MODE_REQUEST, ACCEPT_MODE contains _)
    out <- scheduleGet(OUT_REQUEST, ACCEPT_OUT contains _)
    nDays <- scheduleGet(DAYS_REQUEST, isNumber)
    nCreatures <- scheduleGet(CREATURES_REQUEST, isNumber)
    nFood <- scheduleGet(FOOD_REQUEST, isNumber)
  } yield (mode, out, nDays.toInt, nCreatures.toInt, nFood.toInt) match {
    case (m, o, days, creatures, food) if (m equals "1") && (o equals "y") => utils.SimulationParameters(BaseView(FilePrinter)(Option.empty), days, creatures, food)
    case (m, o, days, creatures, food) if (m equals "2") && (o equals "y") => utils.SimulationParameters(BaseView(FilePrinter)(Option(buildFrame())), days, creatures, food)
    case (m, o, days, creatures, food) if (m equals "2") && (o equals "n") => utils.SimulationParameters(BaseView(CLIPrinter)(Option(buildFrame())), days, creatures, food)
    case (_, _, days, creatures, food) => utils.SimulationParameters(BaseView(CLIPrinter)(Option.empty), days, creatures, food)
  }

  /** Print a [[String]] on standard output. Can be used in a for-comprehension statement.
   * */
  def putStrLn(str: String): IO[Unit] = IO(println(str))

  /** Updates the [[BaseView]] to show the current [[GraphicalEnvironment]] in a different way based on the runtime type of
   * the `sView` parameter
   */
  def update(sView: SimulationView, environment: GraphicalEnvironment): IO[Unit] = sView match {
    case view: BaseView => IO {view.update(updateJFrame(environment, view.frame))}
    case _ => IO {}
  }

  /** Executes the print function of trait [[SimulationView]] printing stats.
   *
   * @param sView  the SimulationView
   * @param output to print
   * @return unit wrapped by the monad IO.
   */
  def print(sView: SimulationView, output: Output): IO[Unit] = IO {
    sView.print(output)
  }

  /** Read a [[String]] from standard input. Can be used in a for-comprehension statement.
   * */
  def getStrLn: IO[String] = IO(scala.io.StdIn.readLine())

}
