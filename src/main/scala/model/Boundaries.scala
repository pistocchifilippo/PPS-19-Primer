package model

import model.Position.Position

case class Boundaries(topLeft: Position, bottomRight: Position)

object Boundaries {

  implicit val isInside: (Boundaries, Position) => Boolean = (bounds, pos) => {
    pos._1 <= bounds.bottomRight._1 &&
    pos._1 >= bounds.topLeft._1 &&
    pos._2 <= bounds.bottomRight._2 &&
    pos._2 >= bounds.topLeft._2
  }

  implicit val isOnBorder: (Boundaries, Position) => Boolean = (bounds, pos) => {
    pos._1 == bounds.bottomRight._1 ||
    pos._1 == bounds.topLeft._1 ||
    pos._2 == bounds.bottomRight._2 ||
    pos._2 == bounds.topLeft._2
  }

}