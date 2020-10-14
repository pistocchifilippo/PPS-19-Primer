package model

import cats.effect.IO
import helpers.io.IoConversion._
import model.creature.movement.EnvironmentCreature.{AteCreature, EnvironmentCreature, ReproducingCreature, StarvingCreature}
import model.environment.Position.Position
import model.creature.movement.EnvironmentCreature
import org.scalatest.funsuite.AnyFunSuite
import testsUtil.Mock._

class CreatureTest extends AnyFunSuite {

  implicit val k: (Double, Double) => Double = EnvironmentCreature.kineticConsumption
  implicit val randomPos: () => Position = MOCK_POS_GENERATOR

  test("A non ReproducingCreature should not reproduce") {

    val test: IO[Unit] = for {
      c1 <- mockStarving
      c2 <- mockAte
      ch1 = c1.reproduce(MOCK_MUTATION)(MOCK_MUTATION)
      ch2 = c2.reproduce(MOCK_MUTATION)(MOCK_MUTATION)
    } yield {
      assert(ch1.isEmpty)
      assert(ch2.isEmpty)
    }

    test.unsafeRunSync()

  }

  test("A ReproducingCreature should reproduce") {

    val test: IO[Unit] = for {
      c <- mockReproducing
      ch = c.reproduce(MOCK_MUTATION)(MOCK_MUTATION)
    } yield {
      assert(ch.nonEmpty)
    }

    test.unsafeRunSync()

  }

  test("The new creature should be a StarvingCreature") {

    val test: IO[Unit] = for {
      c <- mockReproducing
    } yield for {
      ch <- c.reproduce(MOCK_MUTATION)(MOCK_MUTATION)
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
      cs <- mockStarving
      ca <- mockAte
      cr <- mockReproducing
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
      c <- mockStarving
      m <- c.move(EnvironmentCreature.kineticConsumption)
    } yield {
      testIfHasEnergy(c,EnvironmentCreature.kineticConsumption)(() => {assert(!{m.center equals c.center})})
    }

    test.unsafeRunSync()

  }

  test("Creature class should not change") {

    val test: IO[Unit] = for {
      cs <- mockStarving
      ca <- mockAte
      cr <- mockReproducing
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
      c <- mockStarving
      m <- c.move
    } yield {
      testIfHasEnergy(c,EnvironmentCreature.kineticConsumption)(() => {assert(c.energy > m.energy)})
    }

    test.unsafeRunSync()

  }

  private def testIfHasEnergy(c: EnvironmentCreature, energyConsumption: (Double,Double) => Double)(assertion: () => Unit): Unit =
    if (c.energy - EnvironmentCreature.kineticConsumption(c.radius,c.speed)>0) {assertion()}

}