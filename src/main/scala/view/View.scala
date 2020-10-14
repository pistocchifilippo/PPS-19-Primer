package view

import helpers.io.IoConversion._
import cats.effect.IO
import helpers.Configurations._
import helpers.Strategies._
import model.environment.Environment.Environment
import model.output.Output.Output
import view.graphic._
import view.utils.SimulationParameters
import view.utils.ViewUtils._

/** This is a top-level module containing view functionalities */
object View {

  /** Retrieves all the parameters.
   *
   * @return a [[SimulationParameters]] element, casted in [[IO]], to be used in a for-comprehension statement. */
  def collectParameters : IO[SimulationParameters] = for {
    _ <- putStrLn(WELCOME)
    mode <- scheduleGet(MODE, getParameters, ACCEPT_MODE contains _)
    out <- scheduleGet(OUT, getParameters, ACCEPT_OUT contains _)
    nDays <- scheduleGet(DAYS, getParameters, isNumber)
    nCreatures <- scheduleGet(CREATURES, getParameters, isNumber)
    nFood <- scheduleGet(FOOD, getParameters, isNumber)
  } yield (mode, out, nDays.toInt, nCreatures.toInt, nFood.toInt) match {
    case (m, o, days, creatures, food) if((m equals "1") && (o equals "y")) => utils.SimulationParameters(BaseView(FilePrinter)(Option.empty), days, creatures, food)
    case (m, o, days, creatures, food) if((m equals "2") && (o equals "y")) => utils.SimulationParameters(BaseView(FilePrinter)(Option(buildFrame())) , days, creatures, food)
    case (m, o, days, creatures, food) if((m equals "2") && (o equals "n"))=> utils.SimulationParameters(BaseView(CLIPrinter)(Option(buildFrame())), days, creatures, food)
    case (_, _, days, creatures, food) => utils.SimulationParameters(BaseView(CLIPrinter)(Option.empty), days, creatures, food)

  }

  /** Updates the [[BaseView]] to show the current [[Environment]] in a different way based on the runtime type of
   * the `sView` parameter
   */
  def update(sView: SimulationView, environment: Environment): IO[Unit] = sView match {
    case view: BaseView => view.update(updateJFrame(environment, view.frame))
    case _ =>
  }

  /** Executes the print function of trait [[SimulationView]] printing stats.
   *
   * @param sView the SimulationView
   * @param output to print
   * @return unit wrapped by the monad IO.
   */
  def print(sView: SimulationView, output: Output): IO[Unit] = {sView.print(output)}

  /** Print a [[String]] on standard output. Can be used in a for-comprehension statement.
   * */
  def putStrLn(str: String): IO[Unit] = IO(println(str))

  /** Read a [[String]] from standard input. Can be used in a for-comprehension statement.
   * */
  def getStrLn: IO[String] = IO(scala.io.StdIn.readLine())

}
