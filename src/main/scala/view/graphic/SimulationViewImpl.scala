package view.graphic

import cats.effect.IO
import javax.swing.JFrame
import model.output.Output.Output

/** `trait` that defines a generic View */
trait SimulationView{
  def print: Output => IO[Unit]
  def update(performUpdate: () => Unit) { performUpdate() }
}

/** Defines a [[SimulationView]] whit an [[Option]]al [[JFrame]] element for GUI
 * */
case class SimulationViewImpl(override val print: Output => IO[Unit])
                             (val frame: Option[JFrame]) extends SimulationView {
}
