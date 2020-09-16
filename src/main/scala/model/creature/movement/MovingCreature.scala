package model.creature.movement

import helpers.Strategies.randomGoal
import model.Position
import model.creature.Creature

trait MovingCreature extends Creature with Movement

object MovingCreature {
  implicit val kineticConsumption: (Double, Double) => Double = (m, v) => 0.5 * m * Math.pow(v, 2)

  def makeEvolutionSet(creatures: Traversable[Creature])(baseEnergy: Double)(pos: () => Position)(sizeMutation: Double => Double)(speedMutation: Double => Double): Traversable[Creature] =
    creatures.flatMap(
      _ match {

        case StarvingCreature(_,_,_,_,_) => Traversable.empty

        case c: MovingCreature =>
          Traversable(StarvingCreature(pos(), c.speed, baseEnergy, c.radius, randomGoal)) ++ {
            c.reproduce(sizeMutation)(speedMutation)(pos) match {
              case Some(a) => Traversable(a)
              case None => Nil
            }
          }
      }
    )
}