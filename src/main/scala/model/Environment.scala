package model

import model.entity._
import model.entity.Creature._

trait BlobEnvironment {
  def boundaries : Boundaries
  def food : Set[Food]
  def creatures : Set[Creature]
}

case class Environment( override val boundaries: Boundaries,
                        override val food: Set[Food],
                        override val creatures : Set[Creature]) extends BlobEnvironment
