package testsUtil

import controller.simulator.DaySimulator
import helpers.Configurations.BOUNDARIES
import helpers.Strategies.makeBoundedFoodCollection
import model.creature.movement.EnvironmentCreature.{AteCreature, EnvironmentCreature, ReproducingCreature, StarvingCreature}
import model.environment.Blob.makeBlobCollection
import model.environment.Position.Position
import model.environment.{Environment, Goal, Position}
import model.environment.Environment._
import view.View.printCLI
import view.graphic.SimulationView

object Mock {

  val MOCK_STEP = 100
  val MOCK_POSITION: Position = 10.0 -> 10.0
  val MOCK_GOAL: Goal = Goal(30.0 -> 30.0, 10)
  val MOCK_SPEED = 10
  val MOCK_RADIUS = 10
  val MOCK_ENERGY = 10
  val MOCK_FOOD_SET_SIZE = 100
  val MOCK_CREATURE_SET_SIZE = 100

  val MOCK_VIEW: SimulationView = SimulationView(printCLI)(Option.empty)
  val MOCK_MUTATION: Double => Double = e => e * 0.1
  val MOCK_POS_GENERATOR: () => Position = () => MOCK_POSITION

  def mockEnvironment: Environment = Environment(BOUNDARIES, makeBoundedFoodCollection(MOCK_FOOD_SET_SIZE), makeBlobCollection(() => mockStarving)(MOCK_CREATURE_SET_SIZE))

  def mockDaySimulator: DaySimulator = DaySimulator(1,MOCK_STEP, MOCK_FOOD_SET_SIZE, mockEnvironment, MOCK_VIEW)

  def mockStarving: EnvironmentCreature = StarvingCreature(
    center = MOCK_POSITION,
    radius = MOCK_RADIUS,
    speed = MOCK_SPEED,
    energy = MOCK_ENERGY,
    goal = MOCK_GOAL
  )

  def randomMockStarving: EnvironmentCreature = StarvingCreature(
    center = Position.RandomPosition(BOUNDARIES),
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
