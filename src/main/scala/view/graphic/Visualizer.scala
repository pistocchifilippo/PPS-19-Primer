package view.graphic

import java.awt.{Graphics, Graphics2D, RenderingHints}

import helpers.Configurations.{VISUALIZER_HEIGHT, VISUALIZER_WIDTH}
import javax.swing.JPanel
import model.environment.{Blob, Environment}
import view.graphic.GraphicalComponent.GraphicalEnvironment

/** Represent a [[JPanel]] that shows the current status of an [[Environment]]
 * */
case class Visualizer(environment: GraphicalEnvironment) extends JPanel {

  override def paint(g: Graphics): Unit = {

    /** Paints an `oval` for each [[Blob]] in the given environment */
    def _paint(g1: Graphics2D) {
      setSize(VISUALIZER_WIDTH, VISUALIZER_HEIGHT)
      g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
      g1.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
      g1.clearRect(0, 0, VISUALIZER_WIDTH, VISUALIZER_WIDTH)
      environment.paint(g1)
    }

    _paint(g.asInstanceOf[Graphics2D])
  }

}
