package view

import java.awt.{Color, Graphics, Graphics2D, RenderingHints}
import java.util.Random

import javax.swing.JPanel
import model.Environment
import helpers.Configurations._
import scalaz.ioeffect.IO

class Visualizer(environment: Environment) extends JPanel {

  override def paint(g: Graphics): Unit = {
    /*

    for {
      _ <- IO.now(setSize(SIMULATOR_WIDTH, SIMULATOR_HEIGHT))
      g2 <- IO.now(g.asInstanceOf[Graphics2D])
      _ <- IO.now(g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON))
      _ <- IO.now(g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY))
      _ <- IO.now(g2.clearRect(0, 0, SIMULATOR_WIDTH, SIMULATOR_HEIGHT))
    } yield {
      environment.creatures.foreach(c => g2.drawOval(c.center.x.toInt, c.center.y.toInt, c.radius.toInt, c.radius.toInt))
      environment.creatures.foreach(c => g2.drawOval(c.center.x.toInt, c.center.y.toInt, c.radius.toInt, c.radius.toInt))
    }

     */
  }

}
