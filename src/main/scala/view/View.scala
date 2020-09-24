package view

import cats.effect.IO
import javax.swing.JFrame
import model.output.Output.Output

trait SimulationView{
  def print: Output => IO[Unit]
  def update(performUpdate: () => Unit) { performUpdate() }
}

case class View(override val print: Output => IO[Unit])
               (val frame: Option[JFrame]) extends SimulationView {
}
