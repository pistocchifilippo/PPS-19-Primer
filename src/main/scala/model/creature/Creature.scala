package model.creature

import model.{Blob, Position}
import helpers.Strategies._
import model.creature.movement.{ReproducingCreature, StarvingCreature}

trait Creature extends Blob {
  def speed: Double
  def energy: Double

  def survive: Boolean = this match {
    case StarvingCreature(_, _, _, _, _) => false
    case _ => true
  }

  def reproduce(sizeMutation: Double => Double)(speedMutation: Double => Double)(implicit newPosition: () => Position): Option[Creature] = this match {
    case ReproducingCreature(_, speed, energy, radius, _) => Option(StarvingCreature(newPosition(), speedMutation(speed), energy, sizeMutation(radius), randomGoal))
    case _ => None
  }

}
