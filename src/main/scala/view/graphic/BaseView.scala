package view.graphic

import javax.swing.JFrame
import model.output.Output.Output
import view.utils.ViewUtils.Printer

/** `trait` that defines a generic View */
trait SimulationView{
  def print: Printer
  def update(performUpdate: () => Unit) { performUpdate() }
}

/** Defines a [[SimulationView]] whit an [[Option]]al [[JFrame]] element for GUI
 * */
case class BaseView(override val print: Output => Unit)
                   (val frame: Option[JFrame]) extends SimulationView {
}
