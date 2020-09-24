package controller.simulator

import cats.effect.IO
import model.Environment
import model.creature.movement.EnvironmentCreature
import model.io.ModelFunctionalities._
import view.io.ViewFunctionalities._
import view.SimulationView

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
    env <- makeNewEnvironment(c)(environment.food)(coll)
    _ <- update(view, env)
    } yield DayStepSimulator (
      executedStep + 1,
      env,
      view
    )
}
