package topLevel

import cats.effect.IO
import helpers.Strategies.randomGoal
import model.Position
import model.creature.movement.{AteCreature, EnvironmentCreature, ReproducingCreature, StarvingCreature}
import model.io.Transitions.evolutionSet
import org.scalatest.funsuite.AnyFunSuite

class ModelFunctionalitiesTest extends AnyFunSuite {

  implicit val k: (Double, Double) => Double = EnvironmentCreature.kineticConsumption
  implicit val randomPos: () => Position = () => Position(10, 10)
  val dummyMutation: Double => Double = e => e * 0.1

  // EVOLUTION SET TESTS
  test("Evolution Set size should be the same") {

    val test: IO[Unit] = for {
      size <- IO {10}
      evSet <- IO {for {x <- 0 until size} yield AteCreature(Position(10, 10),10,10,x, randomGoal)}
      evolve <- evolutionSet(evSet)(randomPos)(dummyMutation)(dummyMutation)
    } yield {
      assert(evolve.size equals size)
    }

    test.unsafeRunSync()

  }

  test("Evolution Set size should be the double") {

    val test: IO[Unit] = for {
      size <- IO {10}
      evSet <- IO {for {x <- 0 until size} yield ReproducingCreature(Position(10, 10),10,10,x, randomGoal)}
      evolve <- evolutionSet(evSet)(randomPos)(dummyMutation)(dummyMutation)
    } yield {
      assert(evolve.size equals size * 2)
    }

    test.unsafeRunSync()

  }

  test("Evolution Set creatures should be Starving creatures"){

    val test: IO[Unit] = for {
      size <- IO {10}
      evSet <- IO {for {x <- 0 until size} yield ReproducingCreature(Position(10, 10),10,10,x, randomGoal)}
      evolve <- evolutionSet(evSet)(randomPos)(dummyMutation)(dummyMutation)
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
