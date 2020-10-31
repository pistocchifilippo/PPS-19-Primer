package view.graphic

import java.awt.Graphics2D

import model.creature.Creature
import model.environment.Blob.Blob
import model.environment.Environment.Environment
import model.environment.Food
import model.environment.Position.Position

/** Module containing graphical/paintable elements */
object GraphicalComponent {

  /**
   * Define a generic paintable element
   * */
  trait GraphicalComponent {
    def paint(graphic: Graphics2D): Unit
  }

  /**
   * Represent a graphical element, a paintable Blob
   *
   * @param radius is the Blob radius
   * @param center is the Blob center
   */
  case class GraphicalBlob(radius: Double, center: Position) extends GraphicalComponent {
    override def paint(graphic: Graphics2D): Unit =
      graphic.drawOval(center.x.toInt, center.y.toInt, radius.toInt, radius.toInt)
  }

  /**
   * Represent a graphical element, a paintable Environment
   *
   * @param creatures is the [[Traversable]] containing the [[Creature]]s
   * @param food      is the [[Traversable]] containing the [[Food]]
   */
  case class GraphicalEnvironment(creatures: Traversable[Creature], food: Traversable[Food]) extends GraphicalComponent {
    override def paint(graphic: Graphics2D): Unit = {
      creatures foreach (_ paint graphic)
      food foreach (_ paint graphic)
    }
  }

  /**
   * Implicit conversion of a Blob into a Graphical element
   * */
  implicit def blobToGraphical(blob: Blob): GraphicalBlob = GraphicalBlob(blob.radius, blob.center)

  /**
   * Implicit conversion of an Environment into a Graphical element
   * */
  implicit def environmentToGraphical(environment: Environment): GraphicalEnvironment =
    GraphicalEnvironment(environment.creatures, environment.food)

}
