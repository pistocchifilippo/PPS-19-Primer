package model.creature

import model.Blob

/** Creature trait */
trait Creature extends Blob {
  def speed: Double
  def energy: Double
}
