package model.creature

import model.environment.Blob.Blob

/** Creature trait */
trait Creature extends Blob {
  def speed: Double
  def energy: Double
}
