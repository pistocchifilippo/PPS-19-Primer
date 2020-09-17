package helpers

import model.{Boundaries, Position}

object Configurations {
  def TOP_LEFT: Position = Position(0, 0)

  def BOTTOM_RIGHT: Position = Position(500, 500)

  def BOUNDARIES: Boundaries = Boundaries(TOP_LEFT, BOTTOM_RIGHT)

  def CREATURES_RADIUS = 10

  def CREATURES_ENERGY = 1000

  def CREATURES_SPEED = 2

  def FOOD_RADIUS = 3

  def GOAL_RADIUS = 2

  def FIRST_DAY = 1

  def OUTPUT_PATH = "output.json"

  def SIMULATOR_HEIGHT = 500
  def SIMULATOR_WIDTH = 500
  def SIMULATOR_TITLE = "NATURAL SELECTION SIMULATOR"
  def UPDATE_TIME_MS = 300
}
