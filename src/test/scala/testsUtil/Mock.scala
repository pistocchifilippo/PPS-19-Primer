package testsUtil

import controller.simulator.DaySimulator
import helpers.Configurations.BOUNDARIES
import helpers.Strategies.{getFrame, makeBoundedFoodCollection, makeOnBoundsCreaturesCollection, printCLI}
import model.{Blob, BlobImplementation, Environment, Position}
import model.creature.movement.EnvironmentCreature.EnvironmentCreature
import model.creature.movement.{AteCreature, EnvironmentCreature, ReproducingCreature, StarvingCreature}
import model.Blob._
import view.View

object Mock {

  val MOCK_STEP = 100
  val MOCK_POSITION: Position = Position(10,10)
  val MOCK_GOAL: Blob = BlobImplementation(Position(30,30), 10)
  val MOCK_SPEED = 10
  val MOCK_RADIUS = 10
  val MOCK_ENERGY = 10
  val MOCK_FOOD_SET_SIZE = 100
  val MOCK_CREATURE_SET_SIZE = 100

  val MOCK_VIEW: View = View(printCLI)(getFrame(false))

  def mockEnvironment: Environment = Environment(BOUNDARIES, makeBoundedFoodCollection(MOCK_FOOD_SET_SIZE), makeBlobCollection(() => mockStarving)(MOCK_CREATURE_SET_SIZE))

  def mockDaySimulator: DaySimulator = DaySimulator(1,MOCK_STEP, MOCK_FOOD_SET_SIZE, mockEnvironment, MOCK_VIEW)

  def mockStarving: EnvironmentCreature = StarvingCreature(
    center = MOCK_POSITION,
    radius = MOCK_RADIUS,
    speed = MOCK_SPEED,
    energy = MOCK_ENERGY,
    goal = MOCK_GOAL
  )

  def mockAte: EnvironmentCreature = AteCreature(
    center = MOCK_POSITION,
    radius = MOCK_RADIUS,
    speed = MOCK_SPEED,
    energy = MOCK_ENERGY,
    goal = MOCK_GOAL
  )

  def mockReproducing: EnvironmentCreature = ReproducingCreature(
    center = MOCK_POSITION,
    radius = MOCK_RADIUS,
    speed = MOCK_SPEED,
    energy = MOCK_ENERGY,
    goal = MOCK_GOAL
  )


}
