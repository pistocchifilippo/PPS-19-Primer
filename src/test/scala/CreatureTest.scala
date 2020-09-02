import model.Position
import model.entity.{AteCreature, ReproducingCreature, StarvingCreature}
import org.scalatest.funsuite.AnyFunSuite

class CreatureTest extends AnyFunSuite {

  import model.entity.Creature._

  test("A non ReproducingCreature should not reproduce") {
    val starvingCreature = StarvingCreature(
      center = Position(10, 10),
      speed = 10,
      energy = 10,
      radius = 10
    )
    val ateCreature = AteCreature(
      center = Position(10, 10),
      speed = 10,
      energy = 10,
      radius = 10
    )

    val child1 = reproduce(starvingCreature)
    val child2 = reproduce(ateCreature)

    assert(child1.isEmpty)
    assert(child2.isEmpty)

  }

  test("A ReproducingCreature should reproduce") {
    val reproducingCreature = StarvingCreature(
      center = Position(10, 10),
      speed = 10,
      energy = 10,
      radius = 10
    )

    val child1 = reproduce(reproducingCreature)

    assert(child1.nonEmpty)

  }

  test("The new creature should be a StarvingCreature") {
    val reproducingCreature = StarvingCreature(
      center = Position(10, 10),
      speed = 10,
      energy = 10,
      radius = 10
    )

    val child = reproduce(reproducingCreature)

    for {
      c <- child
    } yield
      assert (c match {
      case ReproducingCreature(_, _, _, _) => true
      case _ => false
    })

  }

}