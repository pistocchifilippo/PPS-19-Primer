package helpers

import model.{Boundaries, Position}

import scala.util.Random

object Strategies {

  def randomPosition(boundaries: Boundaries): () => Position =
    () => Position(Random.nextInt(boundaries.bottomRight.x.toInt),
      Random.nextInt(boundaries.bottomRight.y.toInt))

}
