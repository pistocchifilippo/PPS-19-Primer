package topLevel

import cats.effect.IO
import model.Blob
import model.creature.movement.{EnvironmentCreature, StarvingCreature}
import model.io.ModelFunctionalities._
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
      c <- IO pure {Blob.makeBlobCollection(() => mockStarving)(100)}
      m <- moveCreatures(c)
    } yield {
      assert(!(c equals m))
    }

    test.unsafeRunSync()

  }

  test("Make new env"){}

  test("Collisions"){}

}
