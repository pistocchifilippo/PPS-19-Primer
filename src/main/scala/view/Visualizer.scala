package view

import java.awt.{Graphics, Graphics2D, RenderingHints}
import helpers.io.IoConversion._
import cats.effect.IO
import helpers.Configurations._
import javax.swing.JPanel
import model.Environment

case class Visualizer(environment: Environment) extends JPanel {

  override def paint(g: Graphics): Unit = {
    def _paint(g1: Graphics2D) {
//      _ <- setSize(SIMULATOR_WIDTH, SIMULATOR_HEIGHT)
//      _ <- g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
//      _ <- g1.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
//      _ <- g1.clearRect(0, 0, SIMULATOR_WIDTH, SIMULATOR_HEIGHT)
//      _ <- environment.creatures.foreach(c => g1.drawOval(c.center.x.toInt, c.center.y.toInt, c.radius.toInt, c.radius.toInt))
//      _ <- environment.food.foreach(f => g1.drawOval(f.center.x.toInt, f.center.y.toInt, f.radius.toInt, f.radius.toInt))
      setSize(VISUALIZER_WIDTH, VISUALIZER_HEIGHT)
      g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
      g1.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
      g1.clearRect(0, 0, VISUALIZER_WIDTH, VISUALIZER_WIDTH)
      environment.creatures.foreach(c => g1.drawOval(c.center.x.toInt, c.center.y.toInt, c.radius.toInt, c.radius.toInt))
      environment.food.foreach(f => g1.drawOval(f.center.x.toInt, f.center.y.toInt, f.radius.toInt, f.radius.toInt))
    }
    _paint(g.asInstanceOf[Graphics2D])
  }

}
