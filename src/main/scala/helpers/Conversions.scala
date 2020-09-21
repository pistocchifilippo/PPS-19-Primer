package helpers

import model.Position

// da spostare in un file a parte
object Conversions {

  implicit class MathPosition(pos: Position){
    def +(position: Position) = Position(
      x = pos.x + position.x,
      y = pos.y + position.y
    )

    def -(position: Position) = Position(
      x = pos.x - position.x,
      y = pos.y - position.y
    )

    def aTan2: Double = Math.atan2(pos.y, pos.x)

  }
}
