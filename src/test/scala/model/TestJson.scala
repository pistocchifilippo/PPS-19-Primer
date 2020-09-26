package model

import cats.effect.IO
import helpers.Strategies.randomGoal
import model.creature.movement.{AteCreature, StarvingCreature}
import org.scalatest.funsuite.AnyFunSuite
import play.api.libs.json.Json
import helpers.io.IoConversion._

import scala.collection.Traversable

class TestJson extends AnyFunSuite {

  import helpers.json.PimpModelJson._

  test("Creature .toJson should be as the given") {
    val test: IO[Unit] = for {
      c <- StarvingCreature(10.0 -> 10.0, 10, 10, 10, randomGoal)
      json = "{\"Creature\":{\"Condition\":\"Starving\",\"Size\":10,\"Speed\":10,\"Position\":{\"X\":10,\"Y\":10}}}"
    } yield {
      assert(Json.parse(json) equals c.creatureToJson)
    }

    test.unsafeRunSync()

  }

  test("Creatures set should be as the given") {
    val test: IO[Unit] = for {
      food <- IO {Traversable(Food(10.0-> 10.0, 10))}
      creatures <- IO {Traversable(StarvingCreature(10.0-> 10.0, 10, 10, 10, randomGoal),AteCreature(10.0-> 10.0, 11, 11, 1, randomGoal))}
      environment <- Environment(Boundaries(10.0-> 10.0, 10.0-> 10.0), food, creatures)
      json <- IO {"{\"Creatures\":[{\"Creature\":{\"Condition\":\"Starving\",\"Size\":10,\"Speed\":10,\"Position\":{\"X\":10,\"Y\":10}}},{\"Creature\":{\"Condition\":\"Ate\",\"Size\":1,\"Speed\":11,\"Position\":{\"X\":10,\"Y\":10}}}]}"}
    } yield {
      assert(Json.parse(json) equals environment.creaturesToJson)
    }

    test.unsafeRunSync()

  }

  test("Environment should be as the given") {
    val test: IO[Unit] = for {
      food <- IO {Traversable(Food(10.0-> 10.0, 10), Food(44.0 -> 1.0, 10))}
      creatures <- IO {Traversable(StarvingCreature(10.0-> 10.0, 10, 10, 10, randomGoal),AteCreature(10.0-> 10.0, 11, 11, 1, randomGoal))}
      environment <- Environment(Boundaries(10.0-> 10.0, 10.0-> 10.0), food, creatures)
      json <- IO {"{\"Environment\":{\"Creatures\":[{\"Creature\":{\"Condition\":\"Starving\",\"Size\":10,\"Speed\":10,\"Position\":{\"X\":10,\"Y\":10}}},{\"Creature\":{\"Condition\":\"Ate\",\"Size\":1,\"Speed\":11,\"Position\":{\"X\":10,\"Y\":10}}}],\"Food\":[{\"Food\":{\"Position\":{\"X\":10,\"Y\":10}}},{\"Food\":{\"Position\":{\"X\":44,\"Y\":1}}}]}}"}
    } yield {
      assert(Json.parse(json) equals environment.environmentToJson)
    }

    test.unsafeRunSync()
  }
}
