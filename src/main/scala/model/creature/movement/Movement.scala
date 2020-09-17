package model.creature.movement

import helpers.Configurations.GOAL_RADIUS
import model.creature.Creature
import model.{Blob, BlobImplementation}
import helpers.Strategies._

trait Movement extends Creature { c: Creature =>
  def goal: Blob
  def move(implicit energyConsumption: (Double, Double) => Double): EnvironmentCreature = c match {
    case ReproducingCreature(center, speed, energy, radius, _) => ReproducingCreature(randomBoundedPosition, speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
    case StarvingCreature(center, speed, energy, radius, _) => StarvingCreature(randomBoundedPosition, speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
    case AteCreature(center, speed, energy, radius, _) => AteCreature(randomBoundedPosition, speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
  }

  private def computeGoal: Movement => Blob = creature => if (Blob.collide(creature)(creature.goal)) BlobImplementation(randomBoundedPosition, GOAL_RADIUS) else creature.goal
}
