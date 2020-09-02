package model.entity

import model.Blob

trait Creature extends Blob {
  def speed: Double
  def energy: Double
}

object Creature