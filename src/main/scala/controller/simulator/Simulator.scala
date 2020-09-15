package controller.simulator

import controller.SimulationController
import helpers.Configurations._
import model.{Environment, Position}
import view.View
import helpers.Strategies._
import model.creature.Creature


trait Simulator extends Iterator [Simulator] {
  def environment: Environment
  def view: View
}

case class DayStepSimulator(environment: Environment, view: View) extends Simulator {

  implicit val kineticConsumption: (Double, Double) => Double =  Creature.kineticConsumption

  override def hasNext: Boolean = environment.creatures.count(_.energy > 0) > 0

  // Here I need to update graphic component
  override def next(): Simulator =  {

    // movimento => nuovo set di creature
    // collisioni => rimozione cibo mangiato => nuovo set di cibo

    println("New Step")

    view.update(environment, view.frame)

    DayStepSimulator(
      Environment(BOUNDARIES,
                  environment.food, // con cibo rimosso
                  environment.creatures),
      view)
  }

}

case class DaySimulator(nFood: Int,
                         nDays: Int,
                         environment: Environment,
                         view: View
                         ) extends Simulator {

  override def hasNext: Boolean = nDays > 0

  override def next(): Simulator ={
    println("[DAY " + nDays + " ]")
    DaySimulator(
      nFood,
      nDays - 1,
      Environment(BOUNDARIES,
                  makeBoundedFoodCollection(nFood),
                  Creature.makeEvolutionSet(consumeDay(DayStepSimulator(environment, view)).environment.creatures)
                                            (CREATURES_ENERGY)
                                            (() => Position.randomEdgePosition(BOUNDARIES))
                                            (p => p)
                                            (s => s)),
      view)
  }

  @scala.annotation.tailrec
  private def consumeDay(dayStepSimulator: Simulator): Simulator =
    if (dayStepSimulator.hasNext) {
      consumeDay(dayStepSimulator.next())
    } else dayStepSimulator

}

class GUIDayStepSimulator(environment: Environment, view: View) extends DayStepSimulator(environment, view) with SimulationController


object prova extends App {
  val env = Environment(BOUNDARIES, makeBoundedFoodCollection(100), makeOnBoundsCreaturesCollection(50))
  val view = View(printCLI)(update)(getFrame(false))
  val s = DayStepSimulator(env, view)
  val sim = DaySimulator(100, 20, env, view)
  sim.next()

}