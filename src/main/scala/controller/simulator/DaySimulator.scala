package controller.simulator

import cats.effect.IO
import helpers.Configurations._
import model.environment.Environment.Environment
import view.View
import view.graphic.SimulationView

/** The [[DaySimulator]] simulates the execution of an entire day */
case class DaySimulator(executedStep: Int,
                        nFood: Int,
                        nDays: Int,
                        environment: Environment,
                        view: SimulationView) extends Simulator {

  /** Tells if there are remaining days to execute.
   *
   * @return true if the simulator can do another step, simulating the following day
   */
  override def hasNext: Boolean = nDays > 0

  /** Executes a an entire day of the simulation
   *
   * @return A new simulator (maybe) ready to simulate another entire day
   */
  override def next(): IO[Simulator] = for {
    _ <- View.putStrLn("Day "+ executedStep)
    _ <- View.putStrLn("Creatures "+ environment.creatures.size)
    _ <- View.putStrLn(environment.creatures.map(_.speed).toString())

    sim <- DayStepSimulator(FIRST_DAY, environment, view).executeAll
    rawEnvironment = sim.environment
  } yield {
    EvolutionSimulator(
      executedStep,
      nFood,
      nDays,
      rawEnvironment,
      view
    )
  }

}
