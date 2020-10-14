package model.creature.movement

import helpers.Strategies.randomGoal
import model.environment.Position.Position
import model.creature.Creature
import model.environment.Goal
import helpers.Configurations.CREATURES_ENERGY

/** Module describing the trait EnvironmentCreature, and other utilities */
object EnvironmentCreature {

  trait EnvironmentCreature extends Creature with Movement {
    /** The creature reproduces if possible
     *
     * @param sizeMutation  of radius
     * @param speedMutation of speed
     * @param newPosition   of the creature
     * @return Some(c) if the creature can reproduce, None if the creature can't reproduce
     */
    def reproduce(sizeMutation: Double => Double)(speedMutation: Double => Double)(implicit newPosition: () => Position): Option[EnvironmentCreature] = this match {
      case ReproducingCreature(_, speed, _, radius, _) => Option(StarvingCreature(newPosition(), speedMutation(speed), CREATURES_ENERGY, sizeMutation(radius), randomGoal))
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

  /** This is a creature that ate no food
   *
   * @param center of the creature
   * @param speed  of the creature
   * @param energy of the creature
   * @param radius of the creature
   * @param goal   of the creature
   */
  case class StarvingCreature(
                               override val center: Position,
                               override val speed: Double,
                               override val energy: Double,
                               override val radius: Double,
                               override val goal: Goal
                             ) extends EnvironmentCreature

  /** This is a creature that ate one food
   *
   * @param center of the creature
   * @param speed  of the creature
   * @param energy of the creature
   * @param radius of the creature
   * @param goal   of the creature
   */
  case class AteCreature(
                          override val center: Position,
                          override val speed: Double,
                          override val energy: Double,
                          override val radius: Double,
                          override val goal: Goal
                        ) extends EnvironmentCreature

  /** This is a creature that ate two food
   *
   * @param center of the creature
   * @param speed  of the creature
   * @param energy of the creature
   * @param radius of the creature
   * @param goal   of the creature
   */
  case class ReproducingCreature(
                                  override val center: Position,
                                  override val speed: Double,
                                  override val energy: Double,
                                  override val radius: Double,
                                  override val goal: Goal
                                ) extends EnvironmentCreature

  /** Kinetic energy formula */
  implicit val kineticConsumption: (Double, Double) => Double = (m, v) => 0.5 * m * {
    Math pow(v, 2)
  }

  implicit val noSizeMutation: Double => Double = m => m

  implicit val noSpeedMutation: Double => Double = s => s

}