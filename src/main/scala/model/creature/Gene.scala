package model.creature

import scala.util.Random

object Gene {

  case class Gene(size: Double, speed: Double)
  def apply(radius: Double, speed: Double): Gene = Gene(radius, speed)

  type GeneMutation = Gene => Gene

  /** The gene does not mutate */
  implicit val noMutation: GeneMutation = g => g

  /** The gene mutate in size and speed by a delta */
  implicit val deltaMutation: GeneMutation = g => {
    val delta = Random.nextDouble()
    Random.nextInt(2) match {
      case 0 => Gene(g.size + delta, g.speed - delta)
      case 1 => Gene(g.size - delta, g.speed + delta)
    }
  }
}
