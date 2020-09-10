package controller

import controller.simulator.DaySimulator
import model.{Environment, Statistics}

import scala.util.control.Breaks.break

/**
 * Controller that executes the simulation
 *
 * @param nDays is the length of the simulation (in days)
 * @param environment is the environment of the simulation
 */
case class AppController(nDays: Int, environment: Environment) {

  var statistics: Map[Int, Statistics] = Map.empty

  /**
   * Execution of the simulation
   *
   * Append a new statistics for every day of simulation executed
   *
   * @return the statistics of the simulation
   */
  def execute: Map[Int, Statistics] = {
    var daySimulator: DaySimulator = DaySimulator(environment)
    for(_ <- 1 to nDays){
      daySimulator.step match {
        case Some(simulator) => {
          statistics = statistics ++ Map(statistics.size + 1 -> Statistics(simulator.environment.creatures))
          daySimulator = DaySimulator(simulator.environment)
        }
        case _ => { break }
      }
    }
    statistics
  }

}
