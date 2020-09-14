package model.creature

trait Movement extends Creature { c: Creature =>
  def move(implicit energyConsumption: (Double, Double) => Double): Creature = ???
}
