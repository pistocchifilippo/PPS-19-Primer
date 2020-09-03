package model.entity

import model.{Blob, Position}

object Creature {

  trait Creature extends Blob {
    def speed: Double
    def energy: Double
  }

  //(sizeMutation: Double => Double)(speedMutation: Double => Double)(defaultEnergy: () => Double)
  def reproduce(creature: Creature)(implicit newPosition: () => Position): Option[Creature] = creature match {
    case ReproducingCreature(_, speed, energy, radius) => Some(StarvingCreature(newPosition(), speed, energy, radius))
    case _ => None
  }

  def survive(creature: Creature): Boolean = creature match {
    case ReproducingCreature(_, _, _, _) => true
    case AteCreature(_, _, _, _) => true
    case _ => false
  }

  def move(creature: Creature)(position: Position): Creature = creature match {
    case AteCreature(_, speed, energy, radius) => AteCreature(position, speed, energy, radius)
    case ReproducingCreature(_, speed, energy, radius) => ReproducingCreature(position, speed, energy, radius)
    case StarvingCreature(_, speed, energy, radius) =>StarvingCreature(position, speed, energy, radius)
  }

}