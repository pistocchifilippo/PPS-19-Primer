package controller

import cats.effect.IO
import controller.simulator.{DaySimulator, Simulator}
import helpers.Configurations.{BOUNDARIES, FIRST_DAY}
import helpers.Strategies.{makeBoundedFoodCollection, makeOnBoundsCreaturesCollection}
import model.output.Output
import model.output.Output._
import view.utils.SimulationParameters
import helpers.io.IoConversion._
import model.environment
import model.environment.Environment

/** This is a top-level module defining controller functionalities */
object Controller {

  /** This function executes a simulation until it's expiredThis function executes a simulation until it's expired
   *
   * @return The output of the simulation
   */
  def execute: Simulator => IO[Output] = _.foldRight(Output())((sim, out) => log(out)(sim.executedStep - 1, sim.environment))

  /** Creates a new [[DaySimulator]] on an environment with given [[SimulationParameters]]
   *
   * @return a [[DaySimulator]]
   * */
  def makeSimulation(param: SimulationParameters): IO[Simulator] = for {
    environment <- environment.Environment(BOUNDARIES, makeBoundedFoodCollection(param.nFood), makeOnBoundsCreaturesCollection(param.nCreatures))
    sim <- IO{DaySimulator(FIRST_DAY, param.nFood, param.nDays, environment, param.view)}
  } yield sim
}
