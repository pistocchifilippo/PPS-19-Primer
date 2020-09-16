package model.creature.movement

import helpers.Configurations.{BOUNDARIES, GOAL_RADIUS}
import model.creature.Creature
import model.Position._
import model.{Blob, BlobImplementation}

trait Movement extends Creature { c: Creature =>
  def goal: Blob
  def move(implicit energyConsumption: (Double, Double) => Double): MovingCreature = c match {
    case ReproducingCreature(center, speed, energy, radius, _) => ReproducingCreature(center, speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
    case StarvingCreature(center, speed, energy, radius, _) => StarvingCreature(center, speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
    case AteCreature(center, speed, energy, radius, _) => AteCreature(center, speed, energy - energyConsumption(radius, speed), radius, computeGoal(this))
  }

  private def computeGoal: Movement => Blob = creature => if (Blob.collide(creature)(creature.goal)) BlobImplementation(randomPosition(BOUNDARIES), GOAL_RADIUS) else creature.goal
}
