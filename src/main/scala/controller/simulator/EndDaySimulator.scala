package controller.simulator
import cats.effect.IO
import helpers.Configurations.BOUNDARIES
import helpers.Strategies.{makeBoundedFoodCollection, randomBoundedEdgePosition}
import model.Model
import model.creature.Gene
import model.creature.Gene.GeneMutation
import model.environment.Environment
import model.environment.Environment.Environment
import view.graphic.SimulationView

case class EndDaySimulator(executedStep: Int,
                           nFood: Int,
                           nDays: Int,
                           environment: Environment,
                           view: SimulationView
                          ) extends Simulator {

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
    creatures <- Model.evolutionSet(environment.creatures)(() => randomBoundedEdgePosition)(deltaMutation)
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


}
