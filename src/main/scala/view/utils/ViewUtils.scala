package view.utils

import java.awt.Dimension
import java.io.{BufferedWriter, File, FileWriter}
import java.sql.Timestamp

import cats.effect.IO
import helpers.Configurations._
import javax.swing.JFrame
import model.output.Output
import model.output.Output.Output
import view.View._
import view.graphic
import view.graphic.GraphicalComponent.GraphicalEnvironment
import view.graphic.SimulationView

/** Utilities module for [[SimulationView]] elements
 * */
object ViewUtils {

  type Printer = Output => Unit

  /** A Get is a request of a parameter to the user */
  type Get = String => IO[String]

  /** Function that takes the given input and accepts or not that string */
  type Acceptor = String => Boolean

  /** A GetScheduler keep asking the request until the input is correct */
  type GetScheduler = (String, Acceptor) => IO[String]

  val timestamp: String = String.valueOf(new Timestamp(System.currentTimeMillis()).toString.replace(" ", "_"))

  /** Update the [[JFrame]] of a [[BaseView]], if present, to display the given [[GraphicalEnvironment]] */
  val updateJFrame: (GraphicalEnvironment, Option[JFrame]) => () => Unit = (environment, jFrame) => () => {

    /** Update the [[JFrame]] with a new [[Visualizer]] that shows the given Environment */
    def _update(frame: JFrame): Unit = {
      Thread.sleep(UPDATE_TIME_MS)
      frame.getContentPane.removeAll()
      frame.getContentPane.add(graphic.Visualizer(environment))
      frame.revalidate()
    }

    if (jFrame.isDefined) _update(jFrame.get)
  }

  /** Retrieves a [[SimulationParameters]] by console
   *
   * @return a [[Get]] element
   * */
  def getParameter: Get = request => for {
    _ <- putStrLn(request)
    in <- getStrLn
  } yield in

  /** Effectuates a request-and-check for a parameter.
   *
   * @return the parameter if it is consistent, based on an given `accept rule`. Requests the parameter again otherwise.
   * */
  def scheduleGet: GetScheduler = (request, accept) => for {
    in <- getParameter(request)
    res <- if (accept(in)) IO {
      in
    } else scheduleGet(request, accept)
  } yield res

  /** Creates a new [[JFrame]] element
   *
   * @return a new `visible` [[JFrame]] with fixed `size` and `title`, as defined in [[helpers.Configurations]]
   * */
  def buildFrame(): JFrame = new JFrame(SIMULATOR_TITLE) {
    setDefaultCloseOperation(3)
    setSize(new Dimension(SIMULATOR_WIDTH, SIMULATOR_HEIGHT))
    setLocationRelativeTo(null)
    setVisible(true)
  }

  /** Strategy to print the given [[Output]] on console
   * */
  object CLIPrinter extends Printer {
    override def apply(output: Output): Unit = println(Output.CliParser(output))
  }

  /** Startegy to print the given [[Output]] in a file
   * */
  object FilePrinter extends Printer {
    override def apply(output: Output): Unit = {
      new File("statistics" + SEPARATOR).mkdirs()
      new BufferedWriter(
        new FileWriter(s"statistics${SEPARATOR}statistics_$timestamp.json")) {
        write(Output.JsonParser(output))
        close()
      }
    }
  }

}
