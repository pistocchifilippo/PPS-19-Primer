package model

import scala.util.Random

case class Position (x: Double, y : Double)

object Position {

//  type Position = (Double, Double)

  implicit class MathPosition(pos: Position){
    def +(position: Position): Position = Position(
      x = pos.x + position.x,
      y = pos.y + position.y
    )

    def -(position: Position): Position = Position(
      x = pos.x - position.x,
      y = pos.y - position.y
    )

    def aTan2: Double = Math.atan2(pos.y, pos.x)

  }

  type BoundedPosition = Boundaries => Position

  object RandomPosition extends BoundedPosition {
    override def apply(bounds: Boundaries): Position =
      Position(
        x = bounds.topLeft.x + (bounds.bottomRight.x - bounds.topLeft.x) * Random.nextDouble(),
        y = bounds.topLeft.y + (bounds.bottomRight.y - bounds.topLeft.y) * Random.nextDouble()
      )
  }

  object RandomEdgePosition extends BoundedPosition {
    override def apply(bounds: Boundaries): Position =
      Random.nextInt(4) match {
        case 0 =>
          // UP
          Position(
            x = Random.nextDouble() * bounds.bottomRight.x + bounds.topLeft.x,
            y = bounds.topLeft.y
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
