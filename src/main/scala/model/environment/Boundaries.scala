package model.environment

import model.environment.Position.Position

case class Boundaries(topLeft: Position, bottomRight: Position)

object Boundaries {

  implicit val isInside: (Boundaries, Position) => Boolean = (bounds, pos) => {
    pos.x <= bounds.bottomRight.x &&
      pos.x >= bounds.topLeft.x &&
      pos.y <= bounds.bottomRight.y &&
      pos.y >= bounds.topLeft.y
  }

  implicit val isOnBorder: (Boundaries, Position) => Boolean = (bounds, pos) => {
    pos.x == bounds.bottomRight.x ||
      pos.x == bounds.topLeft.x ||
      pos.y == bounds.bottomRight.y ||
      pos.y == bounds.topLeft.y
  }

}
