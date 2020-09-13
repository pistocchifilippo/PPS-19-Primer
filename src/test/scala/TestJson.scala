import model.Position
import model.entity.StarvingCreature
import org.scalatest.funsuite.AnyFunSuite
import play.api.libs.json.Json

class TestJson extends AnyFunSuite {

  import helpers.json.PimpModelJson._

  test("Creature .toJson should be the given") {
    val c = StarvingCreature(Position(10,10), 10, 10, 10)
    val json = "{\"Creature\":{\"Condition\":\"Starving\",\"Size\":10,\"Speed\":10,\"X\":10,\"Y\":10}}"

    assert(Json.parse(json) equals c.toJson)
  }


}
