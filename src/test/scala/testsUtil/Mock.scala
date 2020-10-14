package testsUtil

import controller.simulator.DaySimulator
import helpers.Configurations.BOUNDARIES
import helpers.Strategies.makeBoundedFoodCollection
import model.creature.Gene.Gene
import model.creature.movement.EnvironmentCreature
import model.creature.movement.EnvironmentCreature.{AteCreature, EnvironmentCreature, ReproducingCreature, StarvingCreature}
import model.environment.Blob.makeBlobCollection
import model.environment.Position.Position
import model.environment.{Environment, Goal, Position}
import model.environment.Environment._
import view.utils.ViewUtils._
import view.graphic.BaseView

object Mock {

  val MOCK_STEP = 100
  val MOCK_POSITION: Position = 10.0 -> 10.0
  val MOCK_GOAL: Goal = Goal(30.0 -> 30.0, 10)
  val MOCK_SPEED = 10
  val MOCK_RADIUS = 10
  val MOCK_ENERGY: Double = EnvironmentCreature.kineticConsumption(MOCK_RADIUS,MOCK_SPEED) + 1
  val MOCK_FOOD_SET_SIZE = 100
  val MOCK_CREATURE_SET_SIZE = 100

  val MOCK_VIEW: BaseView = BaseView(Output => ())(Option.empty)
  val MOCK_FILE_VIEW: BaseView = BaseView(FilePrinter)(Option.empty)
  val MOCK_DELTA = 0.5
  val MOCK_MUTATION: Gene => Gene = g => Gene(g.size + MOCK_DELTA, g.speed - MOCK_DELTA)
  val MOCK_POS_GENERATOR: () => Position = () => MOCK_POSITION

  def mockEnvironment: Environment = Environment(BOUNDARIES, makeBoundedFoodCollection(MOCK_FOOD_SET_SIZE), makeBlobCollection(() => mockStarving)(MOCK_CREATURE_SET_SIZE))

  def mockDaySimulator: DaySimulator = DaySimulator(1,MOCK_STEP, MOCK_FOOD_SET_SIZE, mockEnvironment, MOCK_VIEW)
  def mockSimulatorFile: DaySimulator = DaySimulator(1,MOCK_STEP, MOCK_FOOD_SET_SIZE, mockEnvironment, MOCK_FILE_VIEW)

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
