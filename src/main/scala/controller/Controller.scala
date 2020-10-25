package controller

import cats.effect.IO
import controller.simulator.{DaySimulator, EvolutionSimulator, Simulator}
import helpers.Configurations.{BOUNDARIES, FIRST_DAY}
import helpers.Strategies.{makeBoundedFoodCollection, makeOnBoundsCreaturesCollection}
import model.environment
import model.output.Output
import model.output.Output.{Output, log}
import view.utils.SimulationParameters

/** This is a top-level module defining controller functionalities */
object Controller {

  /** This function executes a simulation until it's expiredThis function executes a simulation until it's expired
   *
   * @return The output of the simulation
   */
  def execute: Simulator => IO[Output] = _.foldRight(Output())((sim, out) => sim match {
    case EvolutionSimulator(executedStep, _, _, environment, _) => log(out)(executedStep - 1, environment)
    case _ => out
  })

  /** Creates a new [[DaySimulator]] on an environment with given [[SimulationParameters]]
   *
   * @return a [[DaySimulator]]
   * */
  def makeSimulation(param: SimulationParameters): IO[Simulator] = for {
    environment <- IO pure {environment.Environment(BOUNDARIES, makeBoundedFoodCollection(param.nFood), makeOnBoundsCreaturesCollection(param.nCreatures))}
    sim <- IO pure {DaySimulator(FIRST_DAY, param.nFood, param.nDays, environment, param.view)}
  } yield sim
}
