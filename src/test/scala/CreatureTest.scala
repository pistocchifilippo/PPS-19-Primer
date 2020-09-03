import model.Position
import model.entity.IO.IOCreature
import model.entity.{AteCreature, ReproducingCreature, StarvingCreature}
import org.scalatest.funsuite.AnyFunSuite
import scalaz.ioeffect.IO

class CreatureTest extends AnyFunSuite {

  import model.entity.Creature._
  implicit val randomPos: () => Position = () => Position(10, 10)

  test("A non ReproducingCreature should not reproduce") {

    val starvingCreature = StarvingCreature(Position(10, 10),10,10,10)
    val ateCreature = AteCreature(Position(10, 10),10,10,10)

    val child1 = reproduce(starvingCreature)
    val child2 = reproduce(ateCreature)

    assert(child1.isEmpty)
    assert(child2.isEmpty)

  }

  test("A ReproducingCreature should reproduce") {
    val reproducingCreature = ReproducingCreature(Position(10, 10),10,10,10)
    val child = reproduce(reproducingCreature)
    assert(child.nonEmpty)
  }

  test("The new creature should be a StarvingCreature") {
    val reproducingCreature = ReproducingCreature(Position(10, 10),10,10,10)
    val child = reproduce(reproducingCreature)

    for {
      c <- child
    } yield {
      assert(
        c match {
          case StarvingCreature(_, _, _, _) => true
          case _ => false
        }
      )
    }

  }

  test("A StarvingCreature should not survive") {
    val starvingCreature = StarvingCreature(Position(10, 10),10,10,10)
    val cSurvive = survive(starvingCreature)

    assert(!cSurvive)

  }

  test("A AteCreature and a ReproducingCreature should survive") {
    val ateCreature = AteCreature(Position(10, 10),10,10,10)
    val reproducingCreature = ReproducingCreature(Position(10, 10),10,10,10)
    val survive1 = survive(ateCreature)
    val survive2 = survive(reproducingCreature)

    assert(survive1)
    assert(survive2)

  }

}