package model.creature

import model.{Blob, Position}

trait Creature extends Blob {
  def speed: Double
  def energy: Double

  def survive: Boolean = this match {
    case StarvingCreature(_, _, _, _) => false
    case _ => true
  }

  def reproduce(sizeMutation: Double => Double)(speedMutation: Double => Double)(implicit newPosition: () => Position): Option[Creature] = this match {
    case ReproducingCreature(_, speed, energy, radius) => Option(StarvingCreature(newPosition(), speedMutation(speed), energy, sizeMutation(radius)))
    case _ => None
  }

}

object Creature {

  implicit val kineticConsumption: (Double, Double) => Double = (m, v) => 0.5 * m * Math.pow(v, 2)

  def makeEvolutionSet(creatures: Traversable[Creature])(baseEnergy: Double)(pos: () => Position)(sizeMutation: Double => Double)(speedMutation: Double => Double): Traversable[Creature] =
    creatures.flatMap(
      _ match {

        case StarvingCreature(_,_,_,_) => Traversable.empty

        case c: Creature =>
          Traversable(StarvingCreature(pos(), c.speed, baseEnergy, c.radius)) ++ {
            c.reproduce(sizeMutation)(speedMutation)(pos) match {
              case Some(a) => Traversable(a)
              case None => Nil
            }
          }
      }
    )

}
