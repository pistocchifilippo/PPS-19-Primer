package model.creature.movement

import helpers.Configurations.GOAL_RADIUS
import model.creature.Creature
import model.{Blob, BlobImplementation, Position}
import helpers.Strategies._
import model.creature.movement.EnvironmentCreature.EnvironmentCreature

/** Self type, adding movement to a creature */
trait Movement extends Creature { c: Creature =>
  def goal: Blob

  /** Makes the creature move
   *
   * @param energyConsumption how the energy is decreased
   * @return a new creature with a new position and (if reached) a new goal
   */
  def move(implicit energyConsumption: (Double, Double) => Double): EnvironmentCreature = c match {
    case ReproducingCreature(center, speed, energy, radius, goal) => ReproducingCreature(computeNextPosition(center, speed, goal.center), speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
    case StarvingCreature(center, speed, energy, radius, goal) => StarvingCreature(computeNextPosition(center, speed, goal.center), speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
    case AteCreature(center, speed, energy, radius, goal) => AteCreature(computeNextPosition(center, speed, goal.center), speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
  }

  private def computeGoal: Movement => Blob = creature => if (Blob.collide(creature)(creature.goal)) BlobImplementation(randomBoundedPosition, GOAL_RADIUS) else creature.goal

  private def computeNextPosition:  (Position, Double,  Position) => Position = (source, speed, dest) => {
    source + Position(
      x = speed * Math.cos((dest - source).aTan2),
      y = speed * Math.sin((dest - source).aTan2)
    )
  }

}
