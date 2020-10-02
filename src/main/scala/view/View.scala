package view

import java.io.{BufferedWriter, File, FileWriter}
import java.sql.Timestamp

import cats.effect.IO
import helpers.Configurations._
import helpers.Strategies._
import helpers.io.IoConversion._
import javax.swing.JFrame
import model.environment.Environment._
import model.environment.Environment
import model.output.Output
import model.output.Output.Output
import view.graphic.{SimulationView, BaseView}
import view.utils.SimulationParameters
import view.utils.ViewUtils.buildFrame

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


  /** Prints the given [[Output]] on console
   * */
  def printCLI(output: Output): Unit = println(Output.LastDayParser(output))

  /** Prints the given [[Output]] in a file
   * */
  def printFile(output: Output) = {
    new File("statistics"+ SEPARATOR).mkdirs()
    new BufferedWriter(
      new FileWriter(s"statistics${SEPARATOR}statistics_${timestamp}.json")) {
      write(Output.JsonParser(output))
      close()
    }
  }

  val timestamp: String = String.valueOf(new Timestamp(System.currentTimeMillis()).toString.replace(" ", "_"))

  /** Updates the [[BaseView]] to show the current [[Environment]] in a different way based on the runtime type of
   * the `sView` parameter
   */
  def update(sView: SimulationView, environment: Environment): IO[Unit] = sView match {
    case view: BaseView => view.update(updateJFrame(environment, view.frame))
    case _ =>
  }

  /** Update the [[JFrame]] of a [[BaseView]], if present, to display the given [[Environment]] */
  val updateJFrame: (Environment, Option[JFrame]) => () => Unit = (environment, jFrame) => () => {

    /** Update the [[JFrame]] with a new [[Visualizer]] that shows the given Environment*/
    def _update(frame: JFrame): IO[Unit] = for {
      _ <- Thread.sleep(UPDATE_TIME_MS)
      _ <- frame.getContentPane.removeAll()
      _ <- frame.getContentPane.add(graphic.Visualizer(environment))
      _ <- frame.revalidate()
    } yield ()

    if (jFrame.isDefined ) _update(jFrame.get).unsafeRunSync()
  }

}
