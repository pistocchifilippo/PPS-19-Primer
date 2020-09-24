package topLevel

import cats.effect.IO
import model.creature.movement.{EnvironmentCreature, StarvingCreature}
import model.io.ModelFunctionalities.evolutionSet
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

}
