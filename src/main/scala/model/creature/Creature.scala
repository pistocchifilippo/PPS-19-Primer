package model.creature

import model.{Blob, Position}
import helpers.Strategies._
import model.creature.movement.{ReproducingCreature, StarvingCreature}

trait Creature extends Blob {
  def speed: Double
  def energy: Double
}
