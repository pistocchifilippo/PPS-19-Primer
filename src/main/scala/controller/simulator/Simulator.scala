package controller.simulator

import controller.SimulationController
import helpers.Configurations._
import model.entity.{Creature, Food}
import model.{Environment, Position}
import view.View


trait Simulator extends Iterator [Simulator] {
  def environment: Environment
  def view: View
}

case class DayStepSimulator(
                           environment: Environment,
                           view: View
                           ) extends Simulator {

  implicit val kineticConsumption =  Creature.kineticConsumption

  override def hasNext: Boolean = environment.creatures.count(_.energy > 0) > 0

  // Here I need to update graphic component
  override def next(): Simulator =  {

    // movimento => nuovo set di creature
    // collisioni => rimozione cibo mangiato => nuovo set di cibo

    println("New Step")
    environment.creatures.map(c => Creature.move(c)(Position.randomPosition(BOUNDARIES)))

    DayStepSimulator(
      Environment(
        BOUNDARIES,
        environment.food, // con cibo rimosso
        environment.creatures
      ), view
    )
  }

}

case class DaySimulator(
                       nFood: Int,
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
      Environment(
        BOUNDARIES,
        Food(nFood, FOOD_RADIUS)(() => Position.randomPosition(BOUNDARIES)),
        Creature.makeEvolutionSet(consumeDay(
          DayStepSimulator(environment, view)
        ).environment.creatures)(CREATURES_ENERGY)(() => Position.randomEdgePosition(BOUNDARIES))(p => p)(s => s)
      ),
      view
    )}

  @scala.annotation.tailrec
  private def consumeDay(dayStepSimulator: Simulator): Simulator =
    if (dayStepSimulator.hasNext) {
      consumeDay(dayStepSimulator.next())
    }else dayStepSimulator

}

class GUIDayStepSimulator(environment: Environment, view: View) extends DayStepSimulator(environment, view) with SimulationController


object prova extends App {
  //val env = Environment(BOUNDARIES, Food(1000, FOOD_RADIUS)(helpers.Strategies.randomPosition(BOUNDARIES)), Creature.makeSet(2000, CREATURES_RADIUS, CREATURES_ENERGY, CREATURES_SPEED)(helpers.Strategies.randomPosition(BOUNDARIES)))
  //val s = new GUIDayStepSimulator(env)
  //val sim = DaySimulator(100, 20, env)
  //sim.next()

}