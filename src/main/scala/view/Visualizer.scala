package view

import java.awt.{Graphics, Graphics2D, RenderingHints}
import helpers.io.IoConversion._
import cats.effect.IO
import helpers.Configurations._
import javax.swing.JPanel
import model.Environment

/** Represent a [[JPanel]] that shows the current status of an [[Environment]]
 * */
case class Visualizer(environment: Environment) extends JPanel {


  override def paint(g: Graphics): Unit = {

    /** Paints an `oval` for each [[model.Blob]] in the given environment */
    def _paint(g1: Graphics2D) {
      setSize(VISUALIZER_WIDTH, VISUALIZER_HEIGHT)
      g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
      g1.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
      g1.clearRect(0, 0, VISUALIZER_WIDTH, VISUALIZER_WIDTH)
      environment.creatures.foreach(c => g1.drawOval(c.center._1.toInt, c.center._2.toInt, c.radius.toInt, c.radius.toInt))
      environment.food.foreach(f => g1.drawOval(f.center._1.toInt, f.center._2.toInt, f.radius.toInt, f.radius.toInt))
    }

    _paint(g.asInstanceOf[Graphics2D])
  }

}
