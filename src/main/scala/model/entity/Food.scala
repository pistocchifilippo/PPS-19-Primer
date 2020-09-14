package model.entity

import model.{Blob, Position}

import scala.annotation.tailrec

case class Food(
               center: Position,
               radius: Double
               ) extends Blob


object Food {

//  def apply(units: Int, radius: Double)(strategy: () => Position): Traversable[Food] = {
//
//    @tailrec
//    def _apply(u: Int, food: Traversable[Food], position: Position): Traversable[Food] = u match {
//      case _ if food.exists(f => f == Food(position, radius)) => _apply(u, food, strategy())
//      case _ if !food.exists(f => f == Food(position, radius)) && u > 0 => _apply(u - 1, food ++ Set(Food(position, radius)) , strategy())
//      case _ => food
//    }
//    _apply(units, Set.empty, strategy())
//  }
}


