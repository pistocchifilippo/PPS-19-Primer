package controller.simulator

import cats.effect.IO
import helpers.Configurations.BOUNDARIES
import model.{Blob, Environment}
import model.creature.movement.{EnvironmentCreature, ReproducingCreature}
import view.SimulationView
import  model.io.Transitions._

/** The DayStepSimulator represent the simulation for just one step of just one day */
case class DayStepSimulator(executedStep: Int, environment: Environment, view: SimulationView) extends Simulator {

  implicit val kineticConsumption: (Double, Double) => Double =  EnvironmentCreature.kineticConsumption

  /**
   *
   * @return true if the simulator can do another step
   */
  override def hasNext: Boolean = environment.creatures.count(_.energy > 0) > 0

  /** Executes a step of a day simulation
   *
   * @return A new simulator (maybe) ready to simulate another step of the day
   */
  override def next(): IO[Simulator] = for {
    c <- moveCreatures(environment.creatures)
    coll <- collisions(c)(environment.food)
    env <- updateEnvironment(environment)(coll)
    } yield DayStepSimulator (
      executedStep + 1,
      env,
      view
    )
}
