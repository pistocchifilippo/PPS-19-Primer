package model.creature

import model.creature.Gene.Gene
import model.environment.Blob.Blob

/** Creature trait */
trait Creature extends Blob {
  def speed: Double
  def energy: Double
  def gene: Gene = Gene(radius, speed)
}
