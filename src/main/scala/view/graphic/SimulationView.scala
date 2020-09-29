package view.graphic

import javax.swing.JFrame
import model.output.Output.Output

/** `trait` that defines a generic View */
trait BaseView{
  def print: Output => Unit
  def update(performUpdate: () => Unit) { performUpdate() }
}

/** Defines a [[BaseView]] whit an [[Option]]al [[JFrame]] element for GUI
 * */
case class SimulationView(override val print: Output => Unit)
                         (val frame: Option[JFrame]) extends BaseView {
}
