package controller.simulator

import helpers.Configurations._
import helpers.Strategies._
import model.creature.movement.MovingCreature
import model.{Environment, Position}
import view.View


trait Simulator extends Iterator [Simulator] {
  def environment: Environment
  def executedStep: Int
}

case class DayStepSimulator(executedStep: Int, environment: Environment, view: View) extends Simulator {

  implicit val kineticConsumption: (Double, Double) => Double =  MovingCreature.kineticConsumption

  override def hasNext: Boolean = environment.creatures.count(_.energy > 0) > 0

  // Here I need to update graphic component
  override def next(): Simulator =  {

    // movimento => nuovo set di creature
    val creatures = environment.creatures map (_.move)
    // collisioni => rimozione cibo mangiato => nuovo set di cibo
    val food = environment.food // missing collision check

    println("New Step")

    Thread.sleep(2000)
    view.update(environment, view.frame)

    DayStepSimulator(
      executedStep + 1,
      Environment(BOUNDARIES,
        food, // con cibo rimosso
        creatures)
      ,view)
  }

}

case class DaySimulator(executedStep: Int,
                         nFood: Int,
                         nDays: Int,
                         environment: Environment,
                        view: View
                         ) extends Simulator {

  override def hasNext: Boolean = nDays > 0

  override def next(): Simulator ={
    println("[DAY " + nDays + " ]")
    DaySimulator(
      executedStep + 1,
      nFood,
      nDays - 1,
      Environment(BOUNDARIES,
                  makeBoundedFoodCollection(nFood),
                  MovingCreature.makeEvolutionSet(consumeDay(
                    DayStepSimulator(0, environment, view)).environment.creatures)
                                            (() => Position.randomEdgePosition(BOUNDARIES))
                                            (p => p)
                                            (s => s)),
      view)
  }

  @scala.annotation.tailrec
  private def consumeDay(dayStepSimulator: Simulator): Simulator = if (dayStepSimulator.hasNext) consumeDay(dayStepSimulator.next()) else dayStepSimulator

}

//class GUIDayStepSimulator(environment: Environment, view: View) extends DayStepSimulator(environment, view) with SimulationController


object prova extends App {
  val env = Environment(BOUNDARIES, makeBoundedFoodCollection(100), makeOnBoundsCreaturesCollection(50))
  val view = View(printCLI)(update)(getFrame(false))
  val s = DayStepSimulator(0, env, view)
  val sim = DaySimulator(0, 100, 20, env, view)
  sim.next()
}