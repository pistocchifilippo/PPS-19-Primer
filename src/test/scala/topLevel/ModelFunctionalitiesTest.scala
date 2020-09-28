package topLevel

import cats.effect.IO
import helpers.Configurations.BOUNDARIES
import helpers.Strategies.makeBoundedFoodCollection
import helpers.io.IoConversion._
import model.Blob.makeBlobCollection
import model.Position.MathPosition
import model.creature.movement.EnvironmentCreature
import model.creature.movement.EnvironmentCreature.{EnvironmentCreature, StarvingCreature}
import model.io.ModelFunctionalities._
import model.{Blob, Environment}
import org.scalatest.funsuite.AnyFunSuite
import testsUtil.Mock._

class ModelFunctionalitiesTest extends AnyFunSuite {

  implicit val k: (Double, Double) => Double = EnvironmentCreature.kineticConsumption

  // EVOLUTION SET TESTS
  test("Evolution Set size should be the same") {

    val test: IO[Unit] = for {
      size <- IO {10}
      evSet <- IO {for {_ <- 0 until size} yield mockAte}
      evolve <- evolutionSet(evSet)(MOCK_POS_GENERATOR)(MOCK_MUTATION)(MOCK_MUTATION)
    } yield {
      assert(evolve.size equals size)
    }

    test.unsafeRunSync()

  }

  test("Evolution Set size should be the double") {

    val test: IO[Unit] = for {
      size <- IO {10}
      evSet <- IO {for {_ <- 0 until size} yield mockReproducing}
      evolve <- evolutionSet(evSet)(MOCK_POS_GENERATOR)(MOCK_MUTATION)(MOCK_MUTATION)
    } yield {
      assert(evolve.size equals size * 2)
    }

    test.unsafeRunSync()

  }

  test("Evolution Set creatures should be Starving creatures"){

    val test: IO[Unit] = for {
      size <- IO {10}
      evSet <- IO {for {_ <- 0 until size} yield mockReproducing}
      evolve <- evolutionSet(evSet)(MOCK_POS_GENERATOR)(MOCK_MUTATION)(MOCK_MUTATION)
    } yield for {
      c <- evolve
    } yield {
      assert(c match {
        case StarvingCreature(_,_,_,_,_) => true
        case _ => false
      })
    }


    test.unsafeRunSync()

  }

  // Move
  test("Creature position should be different") {

    val test: IO[Unit] = for {
      creatures <- IO pure {Blob.makeBlobCollection(() => mockStarving)(100)}
      movedCreatures <- moveCreatures(creatures)
    } yield {
      assert(!(creatures equals movedCreatures))
    }

    test.unsafeRunSync()

  }

  test("Distance to goal should be less after moving"){

    def getDistanceToGoal(creature: EnvironmentCreature): Double = creature.center distance creature.goal.center

    val test: IO[Unit] = for {
      creature <- mockStarving
      movedCreature <- moveCreatures(Traversable(creature))
    } yield {
      assert(getDistanceToGoal(movedCreature.head) < getDistanceToGoal(creature))
    }

    test.unsafeRunSync()

  }

  test("Test on new Environment: creature eating and food set reducing on collisions"){

    val test: IO[Unit] = for {
      environment <- Environment(BOUNDARIES, makeBoundedFoodCollection(100), makeBlobCollection(() => randomMockStarving)(100))
      movedCreatures <- moveCreatures(environment.creatures)
      coll <- collisions(movedCreatures)(environment.food)
      newEnvironment <- makeNewEnvironment(movedCreatures)(environment.food)(coll)
    } yield
      coll.size match {
        case n if n > 0 => assert(!(environment.food.size equals newEnvironment.food.size))
        case _ => assert(environment.food.size equals newEnvironment.food.size)
    }

    test.unsafeRunSync()

  }


}
