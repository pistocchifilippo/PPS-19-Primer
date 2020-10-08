package view

import helpers.io.IoConversion._
import cats.effect.IO
import helpers.Configurations._
import helpers.Strategies._
import model.environment.Environment.Environment
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
    case ("1", "y", days, creatures, food) => utils.SimulationParameters(BaseView(printFile)(Option.empty), days, creatures, food)
    case ("1", "n", days, creatures, food) => utils.SimulationParameters(BaseView(printCLI)(Option.empty), days, creatures, food)
    case ("2", "y", days, creatures, food) => utils.SimulationParameters(BaseView(printFile)(Option(buildFrame())) , days, creatures, food)
    case ("2", "n", days, creatures, food) => utils.SimulationParameters(BaseView(printCLI)(Option(buildFrame())), days, creatures, food)
  }

  /** Updates the [[BaseView]] to show the current [[Environment]] in a different way based on the runtime type of
   * the `sView` parameter
   */
  def update(sView: SimulationView, environment: Environment): IO[Unit] = sView match {
    case view: BaseView => view.update(updateJFrame(environment, view.frame))
    case _ =>
  }

}
