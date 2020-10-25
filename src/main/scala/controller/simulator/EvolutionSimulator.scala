package controller.simulator

import cats.effect.IO
import helpers.Configurations.BOUNDARIES
import helpers.Strategies.{makeBoundedFoodCollection, randomBoundedEdgePosition}
import model.Model
import model.creature.Gene
import model.creature.Gene.GeneMutation
import model.environment.Environment
import model.environment.Environment.Environment
import view.View
import view.graphic.SimulationView

/** This component simulates the evolution of the species */
case class EvolutionSimulator(executedStep: Int,
                              nFood: Int,
                              nDays: Int,
                              environment: Environment,
                              view: SimulationView) extends Simulator {

  implicit val deltaMutation: GeneMutation = Gene.deltaMutation

  /**
   *
   * @return true if can be done another next call
   */
  override def hasNext: Boolean = true

  /** Does not mutate the state of the object
   *
   * @return An object of type IO[A] where A is the following instance
   */
  override def next(): IO[Simulator] = for {
    //_ <- log()
    creatures <- Model.evolutionSet(environment.creatures)(() => randomBoundedEdgePosition)(deltaMutation) // The evolution of the specie
    food = makeBoundedFoodCollection(nFood)
    env = Environment(BOUNDARIES, food, creatures)
  } yield {
    DaySimulator(
      executedStep + 1,
      nFood,
      nDays - 1,
      env,
      view
    )
  }

  private def log(): IO[Unit] = for {
    _ <- View.putStrLn("")
    _ <- View.putStrLn(s"Day: $executedStep")
    _ <- View.putStrLn("Reproducing creature: " + Environment.reproducingCreature(environment))
    _ <- View.putStrLn("Dead creature: " + Environment.dieingCreature(environment))
    _ <- View.putStrLn("Average speed: " + Environment.avgSpeed(environment))
  } yield ()

}
