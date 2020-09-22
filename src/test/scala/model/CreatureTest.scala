package model

import cats.effect.IO
import helpers.Strategies._
import model.creature.movement.{AteCreature, EnvironmentCreature, ReproducingCreature, StarvingCreature}
import org.scalatest.funsuite.AnyFunSuite
import model.io.Transitions._
import helpers.io.IoConversion._

class CreatureTest extends AnyFunSuite {

  implicit val k: (Double, Double) => Double = EnvironmentCreature.kineticConsumption
  implicit val randomPos: () => Position = () => Position(10, 10)
  val dummyMutation: Double => Double = e => e * 0.1

  test("A non ReproducingCreature should not reproduce") {

    val test: IO[Unit] = for {
      c1 <- StarvingCreature(Position(10, 10),10,10,10, randomGoal)
      c2 <- AteCreature(Position(10, 10),10,10,10, randomGoal)
      ch1 = c1.reproduce(dummyMutation)(dummyMutation)
      ch2 = c2.reproduce(dummyMutation)(dummyMutation)
    } yield {
      assert(ch1.isEmpty)
      assert(ch2.isEmpty)
    }

    test.unsafeRunSync()

  }

  test("A ReproducingCreature should reproduce") {

    val test: IO[Unit] = for {
      c <- ReproducingCreature(Position(10, 10),10,10,10, randomGoal)
      ch = c.reproduce(dummyMutation)(dummyMutation)
    } yield {
      assert(ch.nonEmpty)
    }

    test.unsafeRunSync()

  }

  test("The new creature should be a StarvingCreature") {

    val test: IO[Unit] = for {
      c <- ReproducingCreature(Position(10, 10),10,10,10, randomGoal)
    } yield for {
      ch <- c.reproduce(dummyMutation)(dummyMutation)
    } yield {
      assert(
        ch match {
          case StarvingCreature(_, _, _, _,_) => true
          case _ => false
        }
      )
    }

    test.unsafeRunSync()

  }

  // Testing feed
  test("Creature should be feed") {
    val test: IO[Unit] = for {
      cs <- StarvingCreature(Position(10, 10),10,10,10, randomGoal)
      ca <- AteCreature(Position(10, 10),10,10,10, randomGoal)
      cr <- ReproducingCreature(Position(10, 10),10,10,10, randomGoal)
    } yield {
      assert(
        cs feed() match {
          case AteCreature(_, _, _, _, _) => true
          case _ => false
        }
      )

      assert(
        ca feed() match {
          case ReproducingCreature(_, _, _, _, _) => true
          case _ => false
        }
      )

      assert(
        cr feed() match {
          case ReproducingCreature(_, _, _, _, _) => true
          case _ => false
        }
      )
    }

    test.unsafeRunSync()

  }

  // Test move
  test("Position should change") {
    val test: IO[Unit] = for {
      c <- StarvingCreature(Position(10, 10),10,10,10, randomGoal)
      m <- c.move(EnvironmentCreature.kineticConsumption)
    } yield {
      //assert(!{movedCreature.center equals creature.center})
    }

    test.unsafeRunSync()

  }

  test("Creature class should not change") {

    val test: IO[Unit] = for {
      cs <- StarvingCreature(Position(10, 10),10,10,10, randomGoal)
      ca <- AteCreature(Position(10, 10),10,10,10, randomGoal)
      cr <- ReproducingCreature(Position(10, 10),10,10,10, randomGoal)
    } yield {
      assert(
        cs.move match {
          case StarvingCreature(_, _, _, _, _) => true
          case _ => false
        }
      )

      assert(
        ca.move match {
          case AteCreature(_, _, _, _, _) => true
          case _ => false
        }
      )

      assert(
        cr.move match {
          case ReproducingCreature(_, _, _, _, _) => true
          case _ => false
        }
      )
    }

    test.unsafeRunSync()

  }

  test("Energy should be lower") {
    val test: IO[Unit] = for {
      energy <- IO {10000}
      c <- StarvingCreature(Position(10, 10),10, energy,20, randomGoal)
      m <- c.move
    } yield {
      assert(m.energy < energy)
    }

    test.unsafeRunSync()

  }

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