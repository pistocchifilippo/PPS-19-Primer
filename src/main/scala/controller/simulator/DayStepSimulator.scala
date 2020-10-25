package controller.simulator

import cats.effect.IO
import model.Model
import model.creature.movement.EnvironmentCreature
import model.environment.Environment
import model.environment.Environment.Environment
import view.View
import view.graphic.GraphicalComponent.environmentToGraphical
import view.graphic.SimulationView // This is the implicit conversion for the Environment

/** The [[DayStepSimulator]] represents the simulation for just one step of just one day */
case class DayStepSimulator(executedStep: Int,
                            environment: Environment,
                            view: SimulationView) extends Simulator {

  implicit val kineticConsumption: (Double, Double) => Double = EnvironmentCreature.kineticConsumption

  /** Tells if simulation can go further.
   *
   * @return true if the simulator can do another step
   */
  override def hasNext: Boolean =
    Environment.atLeastOneWithEnergy(environment) &&
      Environment.isFoodRemaining(environment) &&
      Environment.notAllYetReproducing(environment)

  /** Executes a step of a [[DayStepSimulator]]
   *
   * @return A new [[Simulator]] (maybe) ready to simulate another step of the day
   */
  override def next(): IO[Simulator] = for {
    c <- Model.moveCreatures(environment.creatures)
    coll <- Model.collisions(c)(environment.food)
    env <- Model.makeNewEnvironment(c)(environment.food)(coll)
    _ <- View.update(view, env) // acting an implicit conversion on environment
  } yield {
    DayStepSimulator(
      executedStep + 1,
      env,
      view
    )
  }
}
