package model

import cats.effect.IO
import helpers.Strategies.randomGoal
import model.creature.movement.{AteCreature, StarvingCreature}
import org.scalatest.funsuite.AnyFunSuite
import play.api.libs.json.Json

import scala.collection.Traversable

class TestJson extends AnyFunSuite {

  import helpers.json.PimpModelJson._

  test("Creature .toJson should be as the given") {
    val test: IO[Unit] = for {
      c <- IO {StarvingCreature(Position(10,10), 10, 10, 10, randomGoal)}
      json <- IO {"{\"Creature\":{\"Condition\":\"Starving\",\"Size\":10,\"Speed\":10,\"Position\":{\"X\":10,\"Y\":10}}}"}
    } yield {
      assert(Json.parse(json) equals c.creatureToJson)
    }

    test.unsafeRunSync()

  }

  test("Creatures set should be as the given") {
    val test: IO[Unit] = for {
      food <- IO {Traversable(Food(Position(10,10), 10))}
      creatures <- IO {Traversable(StarvingCreature(Position(10,10), 10, 10, 10, randomGoal),AteCreature(Position(10,10), 11, 11, 1, randomGoal))}
      environment <- IO {Environment(Boundaries(Position(10,10), Position(10,10)), food, creatures)}
      json <- IO {"{\"Creatures\":[{\"Creature\":{\"Condition\":\"Starving\",\"Size\":10,\"Speed\":10,\"Position\":{\"X\":10,\"Y\":10}}},{\"Creature\":{\"Condition\":\"Ate\",\"Size\":1,\"Speed\":11,\"Position\":{\"X\":10,\"Y\":10}}}]}"}
    } yield {
      assert(Json.parse(json) equals environment.creaturesToJson)
    }

    test.unsafeRunSync()

  }

  test("Environment should be as the given") {
    val test: IO[Unit] = for {
      food <- IO {Traversable(Food(Position(10,10), 10), Food(Position(44,1), 10))}
      creatures <- IO {Traversable(StarvingCreature(Position(10,10), 10, 10, 10, randomGoal),AteCreature(Position(10,10), 11, 11, 1, randomGoal))}
      environment <- IO {Environment(Boundaries(Position(10,10), Position(10,10)), food, creatures)}
      json <- IO {"{\"Environment\":{\"Creatures\":[{\"Creature\":{\"Condition\":\"Starving\",\"Size\":10,\"Speed\":10,\"Position\":{\"X\":10,\"Y\":10}}},{\"Creature\":{\"Condition\":\"Ate\",\"Size\":1,\"Speed\":11,\"Position\":{\"X\":10,\"Y\":10}}}],\"Food\":[{\"Food\":{\"Position\":{\"X\":10,\"Y\":10}}},{\"Food\":{\"Position\":{\"X\":44,\"Y\":1}}}]}}"}
    } yield {
      assert(Json.parse(json) equals environment.environmentToJson)
    }

    test.unsafeRunSync()
  }
}
