package model.environment

import model.creature.movement.EnvironmentCreature.{EnvironmentCreature, ReproducingCreature}

object Environment {

  trait Environment {
    def boundaries : Boundaries
    def food : Traversable[Food]
    def creatures : Traversable[EnvironmentCreature]
  }

  case class BlobEnvironment( override val boundaries: Boundaries,
                              override val food: Traversable[Food],
                              override val creatures : Traversable[EnvironmentCreature]) extends Environment

  def apply(boundaries: Boundaries, food: Traversable[Food], creatures: Traversable[EnvironmentCreature]): BlobEnvironment =
    BlobEnvironment(boundaries, food, creatures)

  /**
   *
   * @param environment the target environment
   * @return true if there's at least one creature that has energy grater than zero.
   */
  def atLeastOneWithEnergy(environment: Environment): Boolean = environment.creatures.count(_.energy > 0) > 0

  /**
   *
   * @param environment the target environment
   * @return true if there's at least one unity of food in the environment.
   */
  def isFoodRemaining(environment: Environment): Boolean = environment.food.nonEmpty

  /**
   *
   * @param environment the target environment
   * @return true if all creature are [[ReproducingCreature]]
   */
  def allYetReproducing(environment: Environment): Boolean = environment.creatures.forall { case _: ReproducingCreature => true; case _ => false }

}