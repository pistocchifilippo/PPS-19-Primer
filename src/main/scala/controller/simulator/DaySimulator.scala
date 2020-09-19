package controller.simulator

import cats.effect.IO
import helpers.Configurations.BOUNDARIES
import helpers.Strategies.{makeBoundedFoodCollection, randomBoundedPosition}
import model.Environment
import view.SimulationView
import helpers.Strategies._
import model.creature.movement.EnvironmentCreature._
import helpers.io.IoConversion._

/** The day simulator execute an entire day per step */
case class DaySimulator(executedStep: Int,
                        nFood: Int,
                        nDays: Int,
                        environment: Environment,
                        view: SimulationView
                       ) extends Simulator {

  /**
   *
   * @return true if the simulator can do another step, simulating the following day
   */
  override def hasNext: Boolean = nDays > 0

  /** Executes a an entire day of the simulation
   *
   * @return A new simulator (maybe) ready to simulate another entire day
   */
  override def next(): IO[Simulator] = for {
    _ <- putStrLn("[DAY " + nDays + " ]")
    food <- IO {makeBoundedFoodCollection(nFood)}
    sim <- IO {DayStepSimulator(1, environment, view)}
    endSim <- consumeDay(sim)
    endCreatures <- IO {endSim.environment.creatures}
    creatures <- IO {makeEvolutionSet(endCreatures)(() => randomBoundedPosition)(noSizeMutation)(noSpeedMutation)}
    env <- Environment(BOUNDARIES, food, creatures)
  } yield
    DaySimulator(
      executedStep + 1,
      nFood,
      nDays - 1,
      env,
      view
    )

  private def consumeDay(dayStepSimulator: Simulator): IO[Simulator] =
    if (dayStepSimulator.hasNext) for {
      next <- dayStepSimulator.next()
      d <- consumeDay(next)
    } yield d
    else IO {dayStepSimulator}

}
