package model

import model.entity._
import model.entity.Creature._

trait BlobEnvironment {
  def boundaries : Boundaries
  def food : Traversable[Food]
  def creatures : Traversable[Creature]
}

case class Environment( override val boundaries: Boundaries,
                        override val food: Traversable[Food],
                        override val creatures : Traversable[Creature]) extends BlobEnvironment
