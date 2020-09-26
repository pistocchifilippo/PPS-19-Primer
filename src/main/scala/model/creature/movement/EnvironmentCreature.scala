package model.creature.movement

import helpers.Strategies.randomGoal
import model.Position.Position
import model.creature.Creature

/** Module describing the trait EnvironmentCreature, and other utilities */
object EnvironmentCreature {

  trait EnvironmentCreature extends Creature with Movement {
    /** The creature reproduces if possible
     *
     * @param sizeMutation of radius
     * @param speedMutation of speed
     * @param newPosition of the creature
     * @return Some(c) if the creature can reproduce, None if the creature can't reproduce
     */
    def reproduce(sizeMutation: Double => Double)(speedMutation: Double => Double)(implicit newPosition: () => Position): Option[EnvironmentCreature] = this match {
      case ReproducingCreature(_, speed, energy, radius, _) => Option(StarvingCreature(newPosition(), speedMutation(speed), energy, sizeMutation(radius), randomGoal))
      case _ => None
    }

    /** The creature eat food
     *
     * @return The feed creature
     */
    def feed(): EnvironmentCreature = this match {
      case StarvingCreature(position, speed, energy, radius, goal) => AteCreature(position, speed, energy, radius, goal)
      case AteCreature(position, speed, energy, radius, goal) => ReproducingCreature(position, speed, energy, radius, goal)
      case ReproducingCreature(position, speed, energy, radius, goal) => ReproducingCreature(position, speed, energy, radius, goal)
    }
  }

  /** Kinetic energy formula */
  implicit val kineticConsumption: (Double, Double) => Double = (m, v) => 0.5 * m * {Math pow (v, 2)}

  implicit val noSizeMutation: Double => Double = m => m

  implicit val noSpeedMutation: Double => Double = s => s

}