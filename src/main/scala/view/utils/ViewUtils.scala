package view.utils

import java.awt.Dimension
import java.io.{BufferedWriter, File, FileWriter}
import java.sql.Timestamp
import cats.effect.IO
import helpers.Configurations.{SEPARATOR, SIMULATOR_HEIGHT, SIMULATOR_TITLE, SIMULATOR_WIDTH, UPDATE_TIME_MS}
import javax.swing.JFrame
import helpers.io.IoConversion._
import model.environment.Environment.Environment
import model.output.Output
import model.output.Output.Output
import view.graphic
import view.graphic.{BaseView, SimulationView}

/** Utilities module for [[SimulationView]] elements
 * */
object ViewUtils {

  /** Creates a new [[JFrame]] element
   *
   * @return a new `visible` [[JFrame]] with fixed `size` and `title`, as defined in [[helpers.Configurations]]
   * */
  def buildFrame(): JFrame = new JFrame(SIMULATOR_TITLE){
    setDefaultCloseOperation(3)
    setSize(new Dimension(SIMULATOR_WIDTH, SIMULATOR_HEIGHT))
    setLocationRelativeTo(null)
    setVisible(true)
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
