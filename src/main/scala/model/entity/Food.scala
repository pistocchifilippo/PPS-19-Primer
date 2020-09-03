package model.entity

import model.{Blob, Position}

import scala.annotation.tailrec

case class Food(
               center: Position,
               radius: Double
               ) extends Blob


object Food {

  def apply(units: Int, radius: Double)(strategy: () => Position): Set[Food] = {

    @tailrec
    def _apply(u: Int, food: Set[Food], position: Position): Set[Food] = u match {
      case _ if food.contains(Food(position, radius)) => _apply(u, food, strategy())
      case _ if !food.contains(Food(position, radius)) && u > 0 => _apply(u - 1, food ++ Set(Food(position, radius)) , strategy())
      case _ => food
    }
    _apply(units, Set.empty, strategy())
  }
}


