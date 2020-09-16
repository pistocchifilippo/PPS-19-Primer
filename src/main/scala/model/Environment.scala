package model

import model.creature.movement.MovingCreature

trait BlobEnvironment {
  def boundaries : Boundaries
  def food : Traversable[Food]
  def creatures : Traversable[MovingCreature]
}

case class Environment( override val boundaries: Boundaries,
                        override val food: Traversable[Food],
                        override val creatures : Traversable[MovingCreature]) extends BlobEnvironment
