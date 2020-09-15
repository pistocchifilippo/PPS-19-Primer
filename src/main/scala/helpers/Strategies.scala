package helpers

import javax.swing.JFrame
import model.{Boundaries, Environment, Position}
import view.{View, Visualizer}

import scala.util.Random

object Strategies {

  def randomPosition(boundaries: Boundaries): () => Position =
    () => Position(Random.nextInt(boundaries.bottomRight.x.toInt),
      Random.nextInt(boundaries.bottomRight.y.toInt))


  def printCLI(s: String): Unit = println(s)
  def printFile(s: String): Unit = println(s)

  def update(environment: Environment, jframe: Option[JFrame]): Option[Visualizer] = jframe match {
    case Some(frame) => {
      frame.getContentPane.removeAll()
      val visualizer = Visualizer(environment)
      frame.getContentPane.add(visualizer)
      Option(visualizer)
    }
    case _ => Option.empty
  }

  def getFrame(bool: Boolean): Option[JFrame] = bool match {
    case _ if bool => Option(View.buildFrame())
    case _ => Option.empty
  }
}
