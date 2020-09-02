package model.entity

import model.Blob

object Creature {

  trait Creature extends Blob {
    def speed: Double
    def energy: Double
  }

  def reproduce(creature: Creature): Option[Creature] = creature match {
    case ReproducingCreature(pos, speed, energy, radius) => Some(StarvingCreature(pos, speed, energy, radius))
    case _ => None
  }

}