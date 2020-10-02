package model

import cats.effect.IO
import helpers.Strategies.randomGoal
import model.creature.movement.EnvironmentCreature.StarvingCreature
import model.environment.Environment.Environment
import model.environment.{Boundaries, Environment, Food}
import model.output.Output
import model.output.Output.{JsonParser, Output}
import org.scalatest.funsuite.AnyFunSuite

class OutputTest extends AnyFunSuite {

  val out: Output = Map.empty
  val food = Traversable(Food(10.0-> 10.0, 10))
  val creatures = Traversable(StarvingCreature(10.0-> 10.0, 10, 10, 10, randomGoal))
  val environment: Environment = Environment(Boundaries(10.0-> 10.0, 10.0-> 10.0), food, creatures)


  test("Environment should be the same") {
    val test: IO[Unit] = for {
      newOut <- IO.pure{Output.log(out)(1, environment)}
    } yield {
      assert(newOut(1) equals environment)
    }

    test.unsafeRunSync()
  }

  test("Size should be one") {
    val test: IO[Unit] = for {
      newOut <- IO.pure{Output.log(out)(1, environment)}
    } yield {
      assert(newOut.size equals 1)
    }

    test.unsafeRunSync()
  }

  test("Parsed output should be the same") {
    val test: IO[Unit] = for {
      newOut <- IO.pure{Output.log(out)(1, environment)}
      parse = JsonParser(newOut)
      real = "{\"1\":{\"Environment\":{\"Creatures\":[{\"Creature\":{\"Condition\":\"Starving\",\"Size\":10,\"Speed\":10,\"Position\":{\"X\":10,\"Y\":10}}}],\"Food\":[{\"Food\":{\"Position\":{\"X\":10,\"Y\":10}}}]}}}"
    } yield {
      assert(parse.toString equals real)
    }

    test.unsafeRunSync()
  }

}
