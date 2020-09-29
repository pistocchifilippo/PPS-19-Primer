package helpers

import cats.effect.IO
import helpers.Configurations._
import model.environment.Blob.makeBlobCollection
import model.environment.Position.{Position, RandomEdgePosition, RandomPosition}
import model.{environment, _}
import model.creature.movement.EnvironmentCreature.{EnvironmentCreature, StarvingCreature}
import Model.FoodCreatureCollision
import model.environment.{Blob, BlobImplementation, Food, Position}

object Strategies {

  // IO
  def putStrLn(str: String): IO[Unit] = cats.effect.IO(println(str))
  def getStrLn: IO[String] = cats.effect.IO(scala.io.StdIn.readLine())

  // Positions
  def randomBoundedPosition: Position = Position.RandomPosition(BOUNDARIES)
  def randomBoundedEdgePosition: Position = Position.RandomEdgePosition(BOUNDARIES)

  def makeBoundedFoodCollection(nFood: Int): Traversable[Food] = makeBlobCollection(() => environment.Food(RandomPosition(BOUNDARIES), FOOD_RADIUS))(nFood)
  def makeOnBoundsCreaturesCollection(nCreature: Int): Traversable[EnvironmentCreature] =
    makeBlobCollection(() => StarvingCreature(RandomEdgePosition(BOUNDARIES), CREATURES_SPEED, CREATURES_ENERGY, CREATURES_RADIUS, randomGoal))(nCreature)

  def randomGoal: Blob = BlobImplementation(randomBoundedPosition, GOAL_RADIUS)

  // Collisions
  def collidingCreatures(collisions: Traversable[FoodCreatureCollision]): List[EnvironmentCreature] = collisions.map{_._1}.toList
  def collidingFood(collisions: Traversable[FoodCreatureCollision]): List[Food] = collisions.map{_._2}.toList

  def isNumber: String => Boolean = s => s.forall(_.isDigit)

}
