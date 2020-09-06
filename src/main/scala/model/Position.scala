package model

import scala.util.Random

case class Position (x: Double, y : Double)

object Position {

  implicit val randomEdgePosition: Boundaries => Position = bounds => {
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
