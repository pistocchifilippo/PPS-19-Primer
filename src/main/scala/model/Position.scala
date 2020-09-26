package model

import scala.util.Random


//case class Position (x: Double, y : Double)
/** Module that defines a [[Position]]*/
object Position {

  /** Defines a 2D position */
  type Position = (Double, Double)

  /** Defines operations involving [[Position]]s*/
  implicit class MathPosition(pos: Position){

    /** Calculates the sum of the coordinates of two [[Position]]s */
    def +(position: Position): Position = (pos._1 + position._1) -> (pos._2 + position._2)

    /** Calculates the difference of the coordinates of two [[Position]]s */
    def delta(position: Position): Position = (pos._1 - position._1) -> (pos._2 - position._2)

    /** Calculates aTan2 on the specified [[Position]] */
    def aTan2: Double = Math.atan2(pos._2, pos._1)

    /** Calculates the distance between two [[Position]]s */
    def distance(position: Position): Double = {
      Math.sqrt(Math.pow((pos delta position)._1, 2) + Math.pow((pos delta position)._2, 2))
    }
  }

  type BoundedPosition = Boundaries => Position

  object RandomPosition extends BoundedPosition {
    override def apply(bounds: Boundaries): Position =
      (bounds.topLeft._1 + (bounds.bottomRight._1 - bounds.topLeft._1) * Random.nextDouble()) ->
      (bounds.topLeft._2 + (bounds.bottomRight._2 - bounds.topLeft._2) * Random.nextDouble())
  }

  object RandomEdgePosition extends BoundedPosition {
    override def apply(bounds: Boundaries): Position =
      Random.nextInt(4) match {
        case 0 =>
          // UP
           (Random.nextDouble() * bounds.bottomRight._1 + bounds.topLeft._1) -> bounds.topLeft._1
        case 1 =>
          // DOWN
            (Random.nextDouble() * bounds.bottomRight._1 + bounds.topLeft._1) -> bounds.bottomRight._2
        case 2 =>
          // RIGHT
            bounds.bottomRight._1 -> (Random.nextDouble() * bounds.topLeft._2 + bounds.bottomRight._2)
        case 3 =>
          // LEFT
            bounds.topLeft._1 -> (Random.nextDouble() * bounds.topLeft._2 + bounds.bottomRight._2)
      }
  }

}
