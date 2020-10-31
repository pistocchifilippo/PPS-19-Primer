package model.environment

import com.sun.tools.javac.api.DiagnosticFormatter.Configuration
import helpers.Configurations.SQUARE_BOUNDS

import scala.util.Random

/** Module that defines a [[Position]] */
object Position {

  type BoundedPosition = Boundaries => Position

  /** Defines a 2D position */
  trait Position {
    def x: Double
    def y: Double
  }

  case class PositionImpl(x: Double, y: Double) extends Position
  def apply(x: Double, y: Double): Position = PositionImpl(x, y)

  implicit def tupleToPosition(tuple: (Double, Double)): Position = Position(tuple._1, tuple._2)

  /** Defines operations involving [[Position]]s */
  implicit class MathPosition(pos: Position) {

    /** Calculates the sum of the coordinates of two [[Position]]s */
    def +(position: Position): Position = (pos.x + position.x) -> (pos.y + position.y)

    /** Calculates aTan2 on the specified [[Position]] */
    def aTan2: Double = Math.atan2(pos.y, pos.x)

    /** Calculates the distance between two [[Position]]s */
    def distance(position: Position): Double = {
      Math.sqrt(Math.pow((pos delta position).x, 2) + Math.pow((pos delta position).y, 2))
    }

    /** Calculates the difference of the coordinates of two [[Position]]s */
    def delta(position: Position): Position = (pos.x - position.x) -> (pos.y - position.y)
  }

  object RandomPosition extends BoundedPosition {
    override def apply(bounds: Boundaries): Position =
      Position(
        x = bounds.topLeft.x + (bounds.bottomRight.x - bounds.topLeft.x) * Random.nextDouble(),
        y = bounds.topLeft.y + (bounds.bottomRight.y - bounds.topLeft.y) * Random.nextDouble()
      )

  }

  object RandomEdgePosition extends BoundedPosition {
    override def apply(bounds: Boundaries): Position =
      Random.nextInt(SQUARE_BOUNDS) match {
        case 0 =>
          // UP
          Position(
            x = Random.nextDouble() * bounds.bottomRight.x + bounds.topLeft.x,
            y = bounds.topLeft.x
          )

        case 1 =>
          // DOWN
          Position(
            x = Random.nextDouble() * bounds.bottomRight.x + bounds.topLeft.x,
            y = bounds.bottomRight.y
          )

        case 2 =>
          // RIGHT
          Position(
            x = bounds.bottomRight.x,
            y = Random.nextDouble() * bounds.topLeft.y + bounds.bottomRight.y
          )

        case 3 =>
          // LEFT
          Position(
            x = bounds.topLeft.x,
            y = Random.nextDouble() * bounds.topLeft.y + bounds.bottomRight.y
          )
      }
  }



}
