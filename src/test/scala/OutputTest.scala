import model.entity.{Food, StarvingCreature}
import model.output.Output
import model.output.Output.{JsonParser, Output}
import model.{Boundaries, Environment, Position}
import org.scalatest.funsuite.AnyFunSuite

class OutputTest extends AnyFunSuite {

  val out: Output = Map.empty
  val food = Traversable(Food(Position(10,10), 10))
  val creatures = Traversable(StarvingCreature(Position(10,10), 10, 10, 10))
  val environment = Environment(Boundaries(Position(10,10), Position(10,10)), food, creatures)


  test("Environment should be the same") {
    val newOut = Output.log(out)(1, environment)
    assert(newOut(1) equals environment)
  }

  test("Size should be one") {
    val newOut = Output.log(out)(1, environment)
    assert(newOut.size equals 1)
  }

  test("Parsed output should be the same") {
    val newOut = Output.log(out)(1, environment)
    val parse = JsonParser(newOut)
    val real = "{\"1\":{\"Environment\":{\"Creatures\":[{\"Creature\":{\"Condition\":\"Starving\",\"Size\":10,\"Speed\":10,\"Position\":{\"X\":10,\"Y\":10}}}],\"Food\":[{\"Food\":{\"Position\":{\"X\":10,\"Y\":10}}}]}}}"
    assert(parse.toString equals real)
  }

}
