package helpers

import model.{Boundaries, Position}

object Configurations {
  def TOP_LEFT: Position = Position(0, 0)

  def BOTTOM_RIGHT: Position = Position(500, 500)

  def BOUNDARIES: Boundaries = Boundaries(TOP_LEFT, BOTTOM_RIGHT)

  def CREATURES_RADIUS = 15

  def CREATURES_ENERGY = 30000

  def CREATURES_SPEED = 3

  def FOOD_RADIUS = 3

  def GOAL_RADIUS = 2

  def FIRST_DAY = 1

  def OUTPUT_PATH = "output.json"

  def VISUALIZER_HEIGHT = 500
  def VISUALIZER_WIDTH = 500
  def SIMULATOR_HEIGHT = 530
  def SIMULATOR_WIDTH = 510
  def SIMULATOR_TITLE = "NATURAL SELECTION SIMULATOR"
  def UPDATE_TIME_MS = 30

  // Gets
  val WELCOME = "Welcome to natural selection simulator!!!"
  val SM = "1. Simulation mode"
  val TM = "2. Test mode"
  val MODE = "Choose the execution mode " + SM + " " + TM
  val ACCEPT_MODE = List("1","2")

  val OUT = "Output on file? y/n"
  val ACCEPT_OUT = List("y","n")

  val DAYS = "Number of days"
  val CREATURES = "Number of creatures"
  val FOOD = "Number of food"

}
