package model.entity.IO

import model.Position
import model.entity.Food
import scalaz.ioeffect.IO

object IOFood {

  def makeIOFood(center: Position)(radius: Double): IO[Void, Food] = IO.now(Food(center, radius))

  def makeIOFoodSet(units: Int)(radius: Double)(strategy: () => Position): IO[Void, Traversable[Food]] = IO.now(
    Food(units, radius)(strategy)
  )
}
