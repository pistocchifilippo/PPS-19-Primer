package model.creature.movement

import helpers.Configurations.GOAL_RADIUS
import helpers.Strategies._
import model.creature.Creature
import model.creature.movement.EnvironmentCreature._
import model.environment.Position.{MathPosition, Position}
import model.environment.{Blob, Goal}

/** Self type, adding movement to a creature */
trait Movement extends Creature {c: Creature =>

  def goal: Goal

  /** Makes the creature move
   *
   * @param energyConsumption how the energy is decreased
   * @return a new creature with a new position and (if reached) a new goal
   */
  def move(implicit energyConsumption: (Double, Double) => Double): EnvironmentCreature = c match {
    case creature: EnvironmentCreature if c.energy <= 0 => creature
    case _: ReproducingCreature => ReproducingCreature(computeNextPosition(center, speed, goal.center), speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
    case _: StarvingCreature => StarvingCreature(computeNextPosition(center, speed, goal.center), speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
    case _: AteCreature => AteCreature(computeNextPosition(center, speed, goal.center), speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
  }

  /** Compute the next random goal of a moving creature */
  private def computeGoal: Movement => Goal = creature => if (Blob.collide(creature)(creature.goal)) Goal(randomBoundedPosition, GOAL_RADIUS) else creature.goal

  /** Compute the next position of a moving creature, based on `source`, `destination` and `speed`
   *
   * @return a [[Position]]
   */
  private def computeNextPosition: (Position, Double, Position) => Position = (source, speed, dest) => {
    source + (speed * Math.cos((dest delta source).aTan2) -> speed * Math.sin((dest delta source).aTan2))
  }

}
