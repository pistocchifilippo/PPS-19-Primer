package helpers

import helpers.Configurations._
import model.Model.FoodCreatureCollision
import model.creature.movement.EnvironmentCreature.{EnvironmentCreature, StarvingCreature}
import model.environment
import model.environment.Blob.makeBlobCollection
import model.environment.Position.{Position, RandomEdgePosition, RandomPosition}
import model.environment.{Food, Goal, Position}

object Strategies {

  def randomBoundedEdgePosition: Position = Position.RandomEdgePosition(BOUNDARIES)

  def makeBoundedFoodCollection(nFood: Int): Traversable[Food] = makeBlobCollection(() => environment.Food(RandomPosition(BOUNDARIES), FOOD_RADIUS))(nFood)

  def makeOnBoundsCreaturesCollection(nCreature: Int): Traversable[EnvironmentCreature] =
    makeBlobCollection(() => StarvingCreature(RandomEdgePosition(BOUNDARIES), CREATURES_SPEED, CREATURES_ENERGY, CREATURES_RADIUS, randomGoal))(nCreature)

  def randomGoal: Goal = Goal(randomBoundedPosition, GOAL_RADIUS)

  // Positions
  def randomBoundedPosition: Position = Position.RandomPosition(BOUNDARIES)

  // Collisions
  def collidingCreatures(collisions: Traversable[FoodCreatureCollision]): List[EnvironmentCreature] = collisions.map {
    _._1
  }.toList

  def collidingFood(collisions: Traversable[FoodCreatureCollision]): List[Food] = collisions.map {
    _._2
  }.toList

  def isNumber: String => Boolean = s => s.forall(_.isDigit) && s.nonEmpty

}