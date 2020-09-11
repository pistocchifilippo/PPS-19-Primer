import model.entity.{Food, StarvingCreature}
import model.output.Output
import model.{Boundaries, Environment, Position}
import org.scalatest.funsuite.AnyFunSuite

class OutputTest extends AnyFunSuite {

  val out: Output = () => Map.empty
  val food = Traversable(Food(Position(10,10), 10))
  val creatures = Traversable(StarvingCreature(Position(10,10), 10, 10, 10))
  val environment = Environment(Boundaries(Position(10,10), Position(10,10)), food, creatures)


  test("Environment should be the same") {
    val newOut = Output.log(out)(1, environment)

    assert(newOut.out()(1) equals environment)
  }

  test("Size should be one") {
    val newOut = Output.log(out)(1, environment)

    assert(newOut.out().size equals 1)
  }

}
