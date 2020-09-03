package model.entity

import java.io.IOException

import model.{Blob, Position}
import scalaz.ioeffect.IO

object Creature {

  trait Creature extends Blob {
    def speed: Double
    def energy: Double
  }

  def reproduce(creature: Creature)(implicit newPosition: () => Position): Option[Creature] = creature match {
    case ReproducingCreature(pos, speed, energy, radius) => Some(StarvingCreature(newPosition(), speed, energy, radius))
    case _ => None
  }

}