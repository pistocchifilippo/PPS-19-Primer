package controller.simulator

import cats.effect.IO
import helpers.Configurations.BOUNDARIES
import helpers.Strategies.{makeBoundedFoodCollection, randomBoundedPosition}
import model.Environment
import view.SimulationView
import helpers.Strategies._
import model.creature.movement.EnvironmentCreature._
import model.io.Transitions._

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
    endSim <- sim.executeAll
    endCreatures = endSim.environment.creatures
    creatures <- evolutionSet(endCreatures)(() => randomBoundedPosition)(noSizeMutation)(noSpeedMutation)
    env <- IO{Environment(BOUNDARIES, food, creatures)}
  } yield
    DaySimulator(
      executedStep + 1,
      nFood,
      nDays - 1,
      env,
      view
    )

}
