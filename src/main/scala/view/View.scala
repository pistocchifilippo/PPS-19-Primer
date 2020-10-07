package view

import cats.effect.IO
import helpers.Configurations._
import helpers.Strategies._
import view.graphic.BaseView
import view.utils.SimulationParameters
import view.utils.ViewUtils.{buildFrame, _}

/** This is a top-level module containing view functionalities */
object View {

  /** A Get is a request of a parameter to the user */
  type Get = String => IO[String]

  /** Function that takes the given input and accepts or not that string */
  type Acceptor = String => Boolean

  /** A GetScheduler keep asking the request until the input is correct */
  type GetScheduler = (String, Get, Acceptor) => IO[String]

  /** Retrieves a [[SimulationParameters]] by console
   * @return a [[Get]] element
   * */
  def getParameters: Get = request => for {
    _ <- putStrLn(request)
    in <- getStrLn
  } yield in

  /** Effectuates a request-and-check for a parameter.
   *
   * @return the parameter if it is consistent, based on an given `accept rule`. Requests the parameter again otherwise.
   * */
  def scheduleGet: GetScheduler = (request, get, accept) => for {
    in <- get(request)
    res <- if (accept(in)) IO{in} else scheduleGet(request, get, accept)
  } yield res


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

}
