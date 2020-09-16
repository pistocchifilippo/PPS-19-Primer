package model.creature.movement

import helpers.Strategies.randomGoal
import model.Position
import model.creature.Creature
import helpers.Configurations._

trait MovingCreature extends Creature with Movement

object MovingCreature {
  implicit val kineticConsumption: (Double, Double) => Double = (m, v) => 0.5 * m * Math.pow(v, 2)

  def makeEvolutionSet(creatures: Traversable[MovingCreature])(pos: () => Position)(sizeMutation: Double => Double)(speedMutation: Double => Double): Traversable[MovingCreature] =
    creatures.flatMap(
      _ match {

        case StarvingCreature(_,_,_,_,_) => Traversable.empty

        case c: MovingCreature =>
          Traversable(StarvingCreature(pos(), c.speed, CREATURES_ENERGY, c.radius, randomGoal)) ++ {
            c.reproduce(sizeMutation)(speedMutation)(pos) match {
              case Some(a) => Traversable(a.asInstanceOf[MovingCreature])
              case None => Nil
            }
          }
      }
    )
}