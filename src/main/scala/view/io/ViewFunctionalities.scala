package view.io

import java.io.{File, FileWriter}

import cats.effect.IO
import controller.simulator.{DaySimulator, Simulator}
import helpers.Configurations._
import helpers.Strategies.{getStrLn, isNumber, makeBoundedFoodCollection, makeOnBoundsCreaturesCollection, putStrLn}
import helpers.io.IoConversion._
import javax.swing.JFrame
import model.Environment
import model.output.Output
import model.output.Output.Output
import view.utils.ViewUtils.buildFrame
import view.{SimulationParameters, SimulationView, View, Visualizer}

object ViewFunctionalities {

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
    case ("1", "y", days, creatures, food) => SimulationParameters(View(printFile)(Option.empty), days, creatures, food)
    case ("1", "n", days, creatures, food) => SimulationParameters(View(printCLI)(Option.empty), days, creatures, food)
    case ("2", "y", days, creatures, food) => SimulationParameters(View(printFile)(Option(buildFrame())) , days, creatures, food)
    case ("2", "n", days, creatures, food) => SimulationParameters(View(printCLI)(Option(buildFrame())), days, creatures, food)
  }

  /** Creates a new [[DaySimulator]] on an environment with given [[SimulationParameters]]
   *
   * @return a [[DaySimulator]]
   * */
  def makeSimulation(param: SimulationParameters): IO[Simulator] = for {
    environment <- Environment(BOUNDARIES, makeBoundedFoodCollection(param.nFood), makeOnBoundsCreaturesCollection(param.nCreatures))
    sim <- IO{DaySimulator(FIRST_DAY, param.nFood, param.nDays, environment, param.view)}
  } yield sim

  /** Prints the given [[Output]] on console
   * */
  def printCLI(output: Output): IO[Unit] = putStrLn(Output.LastDayParser(output))

  /** Prints the given [[Output]] in a file
   * */
  def printFile(output: Output): IO[Unit] = for {
    file <- new File("hello.json")
    w <- new FileWriter(file)
    _ <- w.write(Output.JsonParser(output))
    _ <- w.close()
  } yield ()

  /** Updates the [[SimulationView]] to show the current [[Environment]] in a different way based on the runtime type of
   * the `sView` parameter
   */
  def update(sView: SimulationView, environment: Environment): IO[Unit] = sView match {
    case view: View => view.update(updateJFrame(environment, view.frame))
    case _ =>
  }

  /** Update the [[JFrame]] of a [[View]], if present, to display the given [[Environment]] */
  val updateJFrame: (Environment, Option[JFrame]) => () => Unit = (environment, jFrame) => () => {

    /** Update the [[JFrame]] with a new [[Visualizer]] that shows the given Environment*/
    def _update(frame: JFrame): IO[Unit] = for {
      _ <- Thread.sleep(UPDATE_TIME_MS)
      _ <- frame.getContentPane.removeAll()
      _ <- frame.getContentPane.add(Visualizer(environment))
      _ <- frame.revalidate()
    } yield ()

    jFrame match {
      case Some(frame) => _update(frame).unsafeRunSync()
      case _ =>
    }
  }

}
