package model.entity

import model.{Blob, Position}

import scala.annotation.tailrec

object Creature {

  trait Creature extends Blob {
    def speed: Double
    def energy: Double
  }

  implicit val kineticConsumption: (Double, Double) => Double = (m, v) => 0.5 * m * Math.pow(v, 2)

  def makeEvolutionSet(creatures: Set[Creature])(baseEnergy: Double)(pos: () => Position)(sizeMutation: Double => Double)(speedMutation: Double => Double): Set[Creature] =
    creatures.flatMap(c =>
      c match {
        case AteCreature(_, speed, _, radius) => List(StarvingCreature(pos(), speedMutation(speed), baseEnergy, sizeMutation(radius)))
        case ReproducingCreature(_, speed, _, radius) => List(StarvingCreature(pos(), speedMutation(speed), baseEnergy, sizeMutation(radius)), reproduce(c)(pos).get)
        case _ => Nil
      }
    )


  def makeSet(units: Int, radius: Double, energy: Double, speed: Double)(strategy: () => Position): Set[Creature] = {
    @tailrec
    def _apply(u: Int, creatures: Set[Creature], position: Position): Set[Creature] = u match {
      case _ if creatures.contains(StarvingCreature(position, speed, energy, radius)) => _apply(u, creatures, strategy())
      case _ if !creatures.contains(StarvingCreature(position, speed, energy, radius)) && u > 0 => _apply(u - 1, creatures ++ Set(StarvingCreature(position, speed, energy, radius)) , strategy())
      case _ => creatures
    }
    _apply(units, Set.empty, strategy())
  }

  //(sizeMutation: Double => Double)(speedMutation: Double => Double)(defaultEnergy: () => Double)
  def reproduce(creature: Creature)(implicit newPosition: () => Position): Option[Creature] = creature match {
    case ReproducingCreature(_, speed, energy, radius) => Some(StarvingCreature(newPosition(), speed, energy, radius))
    case _ => None
  }

  def survive(creature: Creature): Boolean = creature match {
    case ReproducingCreature(_, _, _, _) => true
    case AteCreature(_, _, _, _) => true
    case _ => false
  }

  def move(creature: Creature)(position: Position)(implicit energyConsumption: (Double, Double) => Double): Creature = creature match {
    case AteCreature(_, speed, energy, radius) => AteCreature(position, speed, energy - energyConsumption(radius, speed), radius)
    case ReproducingCreature(_, speed, energy, radius) => ReproducingCreature(position, speed, energy - energyConsumption(radius, speed), radius)
    case StarvingCreature(_, speed, energy, radius) => StarvingCreature(position, speed, energy - energyConsumption(radius, speed), radius)
  }

}