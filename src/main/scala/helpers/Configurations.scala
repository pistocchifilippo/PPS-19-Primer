package helpers

import model.{Boundaries, Position}

object Configurations {
  def TOP_LEFT: Position = Position(0, 0)

  def BOTTOM_RIGHT: Position = Position(1000, 1000)

  def BOUNDARIES: Boundaries = Boundaries(TOP_LEFT, BOTTOM_RIGHT)

  def CREATURES_RADIUS = 10

  def CREATURES_ENERGY = 200

  def CREATURES_SPEED = 200

  def FOOD_RADIUS = 3
}
