package controller.simulator

import helpers.Configurations._
import model.entity.{Creature, Food}
import model.{Boundaries, Environment, Position}


trait Simulator extends Iterator[Option[Simulator]] // TOGLIERE OPTION

case class DayStepSimulator(
                           environment: Environment
                           ) extends Simulator {

  override def hasNext: Boolean = true

  override def next(): Option[DayStepSimulator] =  {

    // movimento => nuovo set di creature
    // collisioni => rimozione cibo mangiato => nuovo set di cibo

    environment.creatures.count(c => c.energy > 0) match {
      case 0 => None
      case _ => Option(DayStepSimulator(
        Environment(
          environment.boundaries,
          environment.food, // con cibo rimosso
          environment.creatures.map(c => Creature.move(c)(Position.randomPosition(BOUNDARIES))(Creature.kineticConsumption))
        )
      ))
    }
  }
}

case class DaySimulator(
                       nFood: Int,
                       nDays: Int,
                       dayStepSimulator: DayStepSimulator
                       ) extends Simulator {

  override def hasNext: Boolean = true

  override def next(): Option[Simulator] = {
    dayStepSimulator.next() match {
      case Some(s) if s.hasNext => s.next()
      case Some(s) if !s.hasNext =>
        Option(DaySimulator(
          nFood,
          nDays - 1,
          DayStepSimulator(
            Environment(
              BOUNDARIES,
              Food(nFood, FOOD_RADIUS)(() => Position.randomPosition(s.environment.boundaries)),
              Creature.makeEvolutionSet(s.environment.creatures)(CREATURES_ENERGY)(() => Position.randomEdgePosition(BOUNDARIES))(p => p)(s => s)
            )
          )
        ))
    }
  }
}




















//  val boundaries: Boundaries =
//    Boundaries(
//      topLeft = TOP_LEFT,
//      bottomRight = BOTTOM_RIGHT
//    )
//
//  val environment: Environment =
//    Environment(
//      boundaries = boundaries,
//      food = Food(nFood, FOOD_RADIUS)(() => Position.randomPosition(boundaries)),
//      creatures = Creature.makeSet(
//        nCreature,
//        CREATURES_RADIUS,
//        CREATURES_ENERGY,
//        CREATURES_SPEED
//      )(() => Position.randomPosition(boundaries))
//  )

