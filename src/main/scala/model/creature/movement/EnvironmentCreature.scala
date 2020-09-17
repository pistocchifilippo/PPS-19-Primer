package model.creature.movement

import helpers.Strategies.randomGoal
import model.Position
import model.creature.Creature
import helpers.Configurations._

trait EnvironmentCreature extends Creature with Movement {
  def survive: Boolean = this match {
    case StarvingCreature(_, _, _, _, _) => false
    case _ => true
  }

  def reproduce(sizeMutation: Double => Double)(speedMutation: Double => Double)(implicit newPosition: () => Position): Option[EnvironmentCreature] = this match {
    case ReproducingCreature(_, speed, energy, radius, _) => Option(StarvingCreature(newPosition(), speedMutation(speed), energy, sizeMutation(radius), randomGoal))
    case _ => None
  }

  def feed: EnvironmentCreature = this match {
    case StarvingCreature(position, speed, energy, radius, goal) => AteCreature(position, speed, energy, radius, goal)
    case AteCreature(position, speed, energy, radius, goal) => ReproducingCreature(position, speed, energy, radius, goal)
  }
}

object EnvironmentCreature {

  implicit val kineticConsumption: (Double, Double) => Double = (m, v) => 0.5 * m * Math.pow(v, 2)

  implicit val noSizeMutation: Double => Double = m => m

  implicit val noSpeedMutation: Double => Double = s => s

  def makeEvolutionSet(creatures: Traversable[EnvironmentCreature])(pos: () => Position)(sizeMutation: Double => Double)(speedMutation: Double => Double): Traversable[EnvironmentCreature] =
    creatures.flatMap(
      _ match {

        case StarvingCreature(_,_,_,_,_) => Traversable.empty

        case c: EnvironmentCreature =>
          Traversable(StarvingCreature(pos(), c.speed, CREATURES_ENERGY, c.radius, randomGoal)) ++ {
            c.reproduce(sizeMutation)(speedMutation)(pos) match {
              case Some(a) => Traversable(a)
              case None => Nil
            }
          }
      }
    )
}