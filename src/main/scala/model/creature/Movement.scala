package model.creature

import model.Position

trait Movement extends Creature { c: Creature =>
  def goal: Position
  def move(implicit energyConsumption: (Double, Double) => Double): Creature = this match {
    case ReproducingCreature(center, speed, energy, radius) => ReproducingCreature(center, speed, energy, radius)
    case StarvingCreature(center, speed, energy, radius) => StarvingCreature(center, speed, energy, radius)
    case AteCreature(center, speed, energy, radius) => AteCreature(center, speed, energy, radius)
  }
}
