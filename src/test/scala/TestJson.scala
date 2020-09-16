import helpers.Strategies.randomGoal
import model.creature.movement.{AteCreature, StarvingCreature}
import model.{Boundaries, Environment, Food, Position}
import org.scalatest.funsuite.AnyFunSuite
import play.api.libs.json.Json

class TestJson extends AnyFunSuite {

  import helpers.json.PimpModelJson._

  test("Creature .toJson should be as the given") {
    val c = StarvingCreature(Position(10,10), 10, 10, 10, randomGoal)
    val json = "{\"Creature\":{\"Condition\":\"Starving\",\"Size\":10,\"Speed\":10,\"Position\":{\"X\":10,\"Y\":10}}}"

    assert(Json.parse(json) equals c.creatureToJson)
  }

  test("Creatures set should be as the given") {
    val food = Traversable(Food(Position(10,10), 10))
    val creatures = Traversable(StarvingCreature(Position(10,10), 10, 10, 10, randomGoal),AteCreature(Position(10,10), 11, 11, 1, randomGoal))
    val environment = Environment(Boundaries(Position(10,10), Position(10,10)), food, creatures)

    val json = "{\"Creatures\":[{\"Creature\":{\"Condition\":\"Starving\",\"Size\":10,\"Speed\":10,\"Position\":{\"X\":10,\"Y\":10}}},{\"Creature\":{\"Condition\":\"Ate\",\"Size\":1,\"Speed\":11,\"Position\":{\"X\":10,\"Y\":10}}}]}"

    assert(Json.parse(json) equals environment.creaturesToJson)
  }

  test("Environment should be as the given") {
    val food = Traversable(Food(Position(10,10), 10), Food(Position(44,1), 10))
    val creatures = Traversable(StarvingCreature(Position(10,10), 10, 10, 10, randomGoal),AteCreature(Position(10,10), 11, 11, 1, randomGoal))
    val environment = Environment(Boundaries(Position(10,10), Position(10,10)), food, creatures)

    val json = "{\"Environment\":{\"Creatures\":[{\"Creature\":{\"Condition\":\"Starving\",\"Size\":10,\"Speed\":10,\"Position\":{\"X\":10,\"Y\":10}}},{\"Creature\":{\"Condition\":\"Ate\",\"Size\":1,\"Speed\":11,\"Position\":{\"X\":10,\"Y\":10}}}],\"Food\":[{\"Food\":{\"Position\":{\"X\":10,\"Y\":10}}},{\"Food\":{\"Position\":{\"X\":44,\"Y\":1}}}]}}"

    assert(Json.parse(json) equals environment.environmentToJson)
  }
}
