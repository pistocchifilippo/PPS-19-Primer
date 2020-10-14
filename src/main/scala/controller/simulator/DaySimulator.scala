package controller.simulator

import cats.effect.IO
import helpers.Configurations._
import helpers.Strategies._
import model.Model
import model.environment.Environment
import model.environment.Environment.Environment
import view.View.putStrLn
import view.graphic.{BaseView, SimulationView}

/** The [[DaySimulator]] execute an entire day per step */
case class DaySimulator(executedStep: Int,
                        nFood: Int,
                        nDays: Int,
                        environment: Environment,
                        view: SimulationView
                       ) extends Simulator {

  implicit val noMutation: Double => Double = s => s

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
    sim <- DayStepSimulator(FIRST_DAY, environment, view).executeAll
    rawEnvironment = sim.environment
  } yield {
    EndDaySimulator(
      executedStep,
      nFood,
      nDays,
      rawEnvironment,
      view
    )
  }

}
