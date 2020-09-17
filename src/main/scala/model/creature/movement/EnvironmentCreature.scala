package model.creature.movement

import helpers.Strategies.randomGoal
import model.Position
import model.creature.Creature
import helpers.Configurations._

object EnvironmentCreature {

  trait EnvironmentCreature extends Creature with Movement

  implicit val kineticConsumption: (Double, Double) => Double = (m, v) => 0.5 * m * {Math pow (v, 2)}

  implicit val noSizeMutation: Double => Double = m => m

  implicit val noSpeedMutation: Double => Double = s => s

  def makeEvolutionSet(creatures: Traversable[EnvironmentCreature])(pos: () => Position)(sizeMutation: Double => Double)(speedMutation: Double => Double): Traversable[EnvironmentCreature] =
    creatures flatMap {
      _ match {
        case StarvingCreature(_,_,_,_,_) => Traversable.empty
        case c: EnvironmentCreature => Traversable(StarvingCreature(pos(), c.speed, CREATURES_ENERGY, c.radius, randomGoal)) ++ reproduce(c)(sizeMutation)(speedMutation)(pos)
      }
    }

  def reproduce(c: EnvironmentCreature)(sizeMutation: Double => Double)(speedMutation: Double => Double)(implicit newPosition: () => Position): Option[EnvironmentCreature] = c match {
    case ReproducingCreature(_, speed, energy, radius, _) => Option(StarvingCreature(newPosition(), speedMutation(speed), energy, sizeMutation(radius), randomGoal))
    case _ => None
  }

  def feed(c: EnvironmentCreature): EnvironmentCreature = c match {
    case StarvingCreature(position, speed, energy, radius, goal) => AteCreature(position, speed, energy, radius, goal)
    case AteCreature(position, speed, energy, radius, goal) => ReproducingCreature(position, speed, energy, radius, goal)
    case ReproducingCreature(position, speed, energy, radius, goal) => ReproducingCreature(position, speed, energy, radius, goal)
  }
}