package model.environment

import model.creature.movement.EnvironmentCreature.{EnvironmentCreature, ReproducingCreature, StarvingCreature}

object Environment {

  trait Environment {
    def boundaries: Boundaries

    def food: Traversable[Food]

    def creatures: Traversable[EnvironmentCreature]
  }

  case class BlobEnvironment(override val boundaries: Boundaries,
                             override val food: Traversable[Food],
                             override val creatures: Traversable[EnvironmentCreature]) extends Environment

  def apply(boundaries: Boundaries, food: Traversable[Food], creatures: Traversable[EnvironmentCreature]): BlobEnvironment =
    BlobEnvironment(boundaries, food, creatures)

  /**
   *
   * @param environment referring.
   * @return the number of creature that is going to die inside the environment.
   */
  def dieingCreature(environment: Environment): Int = environment.creatures.count {
    case _: StarvingCreature => true
    case _ => false
  }

  /**
   *
   * @param environment referring.
   * @return the number of creature that is going to reproduce.
   */
  def reproducingCreature(environment: Environment): Int = environment.creatures.count {
    case _: ReproducingCreature => true
    case _ => false
  }

  /**
   *
   * @param environment referring.
   * @return the average speed of creatures.
   */
  def avgSpeed(environment: Environment): Double = environment.creatures.map(_.speed).sum / environment.creatures.size

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
  def notAllYetReproducing(environment: Environment): Boolean = !environment.creatures.forall { case _: ReproducingCreature => true; case _ => false }

}
