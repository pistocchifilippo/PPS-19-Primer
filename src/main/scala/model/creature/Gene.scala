package model.creature

import helpers.Configurations.DELTA_MUTATION

import scala.util.Random

object Gene {
  type GeneMutation = Gene => Gene

  def apply(radius: Double, speed: Double): Gene = Gene(radius, speed)

  case class Gene(size: Double, speed: Double)

  /** The gene does not mutate */
  implicit val noMutation: GeneMutation = g => g

  /** The gene mutate in size and speed by a delta */
  implicit val deltaMutation: GeneMutation = g => {
    Random.nextInt(2) match {
      case 0 => Gene(g.size, g.speed * (1 + DELTA_MUTATION))
      case 1 => Gene(g.size, g.speed * (1 - DELTA_MUTATION))
    }
  }
}
