package model

import model.creature.movement.EnvironmentCreature

trait BlobEnvironment {
  def boundaries : Boundaries
  def food : Traversable[Food]
  def creatures : Traversable[EnvironmentCreature]
}

case class Environment( override val boundaries: Boundaries,
                        override val food: Traversable[Food],
                        override val creatures : Traversable[EnvironmentCreature]) extends BlobEnvironment
