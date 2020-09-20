package view

import java.awt.{Color, Graphics, Graphics2D, RenderingHints}
import java.io.IOException
import java.util.Random

import javax.swing.JPanel
import model.Environment
import helpers.Configurations._
import scalaz.ioeffect.IO

case class Visualizer(environment: Environment) extends JPanel {

  override def paint(g: Graphics): Unit = {
    setSize(SIMULATOR_WIDTH, SIMULATOR_HEIGHT)
    val g2 = g.asInstanceOf[Graphics2D]
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
    g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
    g2.clearRect(0, 0, SIMULATOR_WIDTH, SIMULATOR_HEIGHT)
    environment.creatures.foreach(c => g2.drawOval(c.center.x.toInt, c.center.y.toInt, c.radius.toInt, c.radius.toInt))
    environment.food.foreach(f => g2.drawOval(f.center.x.toInt, f.center.y.toInt, f.radius.toInt, f.radius.toInt))
  }

}
