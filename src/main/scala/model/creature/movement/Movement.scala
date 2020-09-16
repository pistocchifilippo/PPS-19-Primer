package model.creature.movement

import helpers.Configurations.GOAL_RADIUS
import helpers.Strategies.randomBoundedPosition
import model.creature.Creature
import model.{Blob, BlobImplementation}

trait Movement extends Creature { c: Creature =>
  def goal: Blob
  def move(implicit energyConsumption: (Double, Double) => Double): MovingCreature = c match {
    case MReproducingCreature(center, speed, energy, radius, _) => MReproducingCreature(center, speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
    case MStarvingCreature(center, speed, energy, radius, _) => MStarvingCreature(center, speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
    case MAteCreature(center, speed, energy, radius, _) => MAteCreature(center, speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
  }

  private def computeGoal: Movement => Blob = creature => if (Blob.collide(creature)(creature.goal)) BlobImplementation(randomBoundedPosition, GOAL_RADIUS) else creature.goal
}
