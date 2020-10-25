package model.creature.movement

import helpers.Configurations.CREATURES_ENERGY
import helpers.Strategies.randomGoal
import model.creature.Creature
import model.creature.Gene.GeneMutation
import model.environment.Goal
import model.environment.Position.Position

/** Module describing the trait EnvironmentCreature, and other utilities */
object EnvironmentCreature {

  trait EnvironmentCreature extends Creature with Movement {

    /** The creature reproduces if possible
     *
     * @param geneMutation of the creature
     * @param newPosition  of the creature
     * @return Some(c) if the creature can reproduce, None if the creature can't reproduce
     */
    def reproduce(geneMutation: GeneMutation)(implicit newPosition: () => Position): Option[EnvironmentCreature] = this match {
      case _: ReproducingCreature =>
        val mutation = geneMutation(this.gene)
        Option(StarvingCreature(newPosition(), mutation.speed, CREATURES_ENERGY, mutation.size, randomGoal))
      case _ => None
    }

    /** The creature eat food
     *
     * @return The feed creature
     */
    def feed(): EnvironmentCreature = this match {
      case _: StarvingCreature => AteCreature(center, speed, energy, radius, goal)
      case _: AteCreature => ReproducingCreature(center, speed, energy, radius, goal)
      case _: ReproducingCreature => ReproducingCreature(center, speed, energy, radius, goal)
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
  case class StarvingCreature(override val center: Position,
                              override val speed: Double,
                              override val energy: Double,
                              override val radius: Double,
                              override val goal: Goal) extends EnvironmentCreature

  /** This is a creature that ate one food
   *
   * @param center of the creature
   * @param speed  of the creature
   * @param energy of the creature
   * @param radius of the creature
   * @param goal   of the creature
   */
  case class AteCreature(override val center: Position,
                         override val speed: Double,
                         override val energy: Double,
                         override val radius: Double,
                         override val goal: Goal) extends EnvironmentCreature

  /** This is a creature that ate two food
   *
   * @param center of the creature
   * @param speed  of the creature
   * @param energy of the creature
   * @param radius of the creature
   * @param goal   of the creature
   */
  case class ReproducingCreature(override val center: Position,
                                 override val speed: Double,
                                 override val energy: Double,
                                 override val radius: Double,
                                 override val goal: Goal) extends EnvironmentCreature

  /** Kinetic energy formula */
  implicit val kineticConsumption: (Double, Double) => Double = (m, v) => 0.5 * m * Math.pow(v, 2)

}