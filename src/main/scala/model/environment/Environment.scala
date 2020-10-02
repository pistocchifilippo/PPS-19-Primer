package model.environment

import model.creature.movement.EnvironmentCreature.EnvironmentCreature




object Environment {

  trait Environment {
    def boundaries : Boundaries
    def food : Traversable[Food]
    def creatures : Traversable[EnvironmentCreature]
  }

  case class BlobEnvironment( override val boundaries: Boundaries,
                              override val food: Traversable[Food],
                              override val creatures : Traversable[EnvironmentCreature]) extends Environment


  def apply(boundaries: Boundaries, food: Traversable[Food], creatures: Traversable[EnvironmentCreature]) =
    BlobEnvironment(boundaries, food, creatures)

}