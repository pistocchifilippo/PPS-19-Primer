package model.creature

import helpers.Configurations.DELTA_MUTATION

import scala.util.Random

object Gene {

  case class Gene(size: Double, speed: Double)
  def apply(radius: Double, speed: Double): Gene = Gene(radius, speed)

  type GeneMutation = Gene => Gene

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
