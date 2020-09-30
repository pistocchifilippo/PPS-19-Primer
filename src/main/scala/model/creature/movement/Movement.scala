package model.creature.movement

import helpers.Configurations.GOAL_RADIUS
import model.creature.Creature
import helpers.Strategies._
import model.creature.movement.EnvironmentCreature._
import model.environment.{Blob, Goal}
import model.environment.Blob.Blob
import model.environment.Position.{MathPosition, Position}

/** Self type, adding movement to a creature */
trait Movement extends Creature { c: Creature =>
  def goal: Goal

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

  private def computeGoal: Movement => Goal = creature => if (Blob.collide(creature)(creature.goal)) Goal(randomBoundedPosition, GOAL_RADIUS) else creature.goal

  /** Compute the next position of a moving creature, based on `source`, `destination` and `speed`
   *
   * @return a [[Position]]
   */
  private def computeNextPosition: (Position, Double,  Position) => Position = (source, speed, dest) => {
    source + (speed * Math.cos((dest delta source).aTan2) -> speed * Math.sin((dest delta source).aTan2))
  }

}
