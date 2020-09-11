package controller.simulator

import helpers.Configurations._
import model.entity.{Creature, Food}
import model.{Environment, Position}


trait Simulator extends Iterator [Simulator]

case class DayStepSimulator(
                           environment: Environment
                           ) extends Simulator {

  implicit val kineticConsumption =  Creature.kineticConsumption

  override def hasNext: Boolean = environment.creatures.count(_.energy > 0) > 0

  // Here I need to update graphic component
  override def next(): DayStepSimulator =  {

    // movimento => nuovo set di creature
    // collisioni => rimozione cibo mangiato => nuovo set di cibo

    DayStepSimulator(
      Environment(
        BOUNDARIES,
        environment.food, // con cibo rimosso
        environment.creatures.map(c => Creature.move(c)(Position.randomPosition(BOUNDARIES)))
      )
    )
  }

}

case class DaySimulator(
                       nFood: Int,
                       nDays: Int,
                       dayStepSimulator: DayStepSimulator
                       ) extends Simulator {

  override def hasNext: Boolean = nDays > 0

  override def next(): DaySimulator =
    DaySimulator(
      nFood,
      nDays - 1,
      DayStepSimulator(
        Environment(
          BOUNDARIES,
          Food(nFood, FOOD_RADIUS)(() => Position.randomPosition(BOUNDARIES)),
          Creature.makeEvolutionSet(consumeDay(dayStepSimulator).environment.creatures)(CREATURES_ENERGY)(() => Position.randomEdgePosition(BOUNDARIES))(p => p)(s => s)
        )
      )
    )

  @scala.annotation.tailrec
  private def consumeDay(dayStepSimulator: DayStepSimulator): DayStepSimulator =
    if (dayStepSimulator.hasNext) consumeDay(dayStepSimulator.next()) else dayStepSimulator

}