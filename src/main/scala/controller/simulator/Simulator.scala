package controller.simulator

import helpers.Configurations._
import model.entity.{Creature, Food}
import model.{Environment, Position}


trait Simulator extends Iterator [Simulator] {
  def environment: Environment
}

case class DayStepSimulator(
                           environment: Environment
                           ) extends Simulator {

  implicit val kineticConsumption =  Creature.kineticConsumption

  override def hasNext: Boolean = environment.creatures.count(_.energy > 0) > 0

  // Here I need to update graphic component
  override def next(): Simulator =  {

    // movimento => nuovo set di creature
    // collisioni => rimozione cibo mangiato => nuovo set di cibo

    println("New Step")

    DayStepSimulator(
      Environment(
        BOUNDARIES,
        environment.food, // con cibo rimosso
        environment.creatures.map(c => Creature.move(c)(Position.randomPosition(BOUNDARIES)))
      )
    )
  }

}

trait GUISimulator extends Simulator {
  private[this] var gui = "GUI"
  abstract override def next(): Simulator = {
    val newSim = super.next()
    //gui.update(newSim.environment)
    println("gui updated")

    //println(newSim.asInstanceOf[DayStepSimulator].environment.creatures)
    new GUIDayStepSimulator(newSim.environment)
  }
}
class GUIDayStepSimulator(environment: Environment) extends DayStepSimulator(environment) with GUISimulator


case class DaySimulator(
                       nFood: Int,
                       nDays: Int,
                       environment: Environment,
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
          DayStepSimulator(environment)
        ).environment.creatures)(CREATURES_ENERGY)(() => Position.randomEdgePosition(BOUNDARIES))(p => p)(s => s)
      )
    )}

  @scala.annotation.tailrec
  private def consumeDay(dayStepSimulator: Simulator): Simulator =
    if (dayStepSimulator.hasNext) {
      consumeDay(dayStepSimulator.next())
    }else dayStepSimulator

}

object prova extends App {
  val env = Environment(BOUNDARIES, Food(1000, FOOD_RADIUS)(helpers.Strategies.randomPosition(BOUNDARIES)), Creature.makeSet(2000, CREATURES_RADIUS, CREATURES_ENERGY, CREATURES_SPEED)(helpers.Strategies.randomPosition(BOUNDARIES)))
//  val s = new GUIDayStepSimulator(env)
  val sim = DaySimulator(100, 20, env)
  sim.next()

}