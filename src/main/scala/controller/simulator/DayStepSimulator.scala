package controller.simulator

import cats.effect.IO
import model.Model
import model.creature.movement.EnvironmentCreature
import model.environment.Environment._
import view.View
import view.graphic.SimulationView

/** The [[DayStepSimulator]] represents the simulation for just one step of just one day */
case class DayStepSimulator(executedStep: Int,
                            environment: Environment,
                            view: SimulationView) extends Simulator {

  implicit val kineticConsumption: (Double, Double) => Double =  EnvironmentCreature.kineticConsumption

  /** Tells if there are remaining creature with energy grater than zero.
   *
   * @return true if the simulator can do another step
   */
  override def hasNext: Boolean = environment.creatures.count(_.energy > 0) > 0

  /** Executes a step of a [[DayStepSimulator]]
   *
   * @return A new [[Simulator]] (maybe) ready to simulate another step of the day
   */
  override def next(): IO[Simulator] = for {
    c <- Model.moveCreatures(environment.creatures)
    coll <- Model.collisions(c)(environment.food)
    env <- Model.makeNewEnvironment(c)(environment.food)(coll)
    _ <- View.update(view, env)
    } yield {
      DayStepSimulator(
        executedStep + 1,
        env,
        view
      )
  }
}
