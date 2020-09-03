import model.Position
import model.entity.IO.IOCreature
import model.entity.{AteCreature, ReproducingCreature, StarvingCreature}
import org.scalatest.funsuite.AnyFunSuite
import scalaz.ioeffect.IO

class CreatureTest extends AnyFunSuite {

  import model.entity.Creature._
  implicit val randomPos: () => Position = () => Position(10, 10)

  test("A non ReproducingCreature should not reproduce") {
    println("avsfdvasfdva")

    for {
      starvingCreature <- IOCreature.makeIOStarvingCreature(Position(10, 10))(10)(10)(10)
      ateCreature <- IOCreature.makeIOAteCreature(Position(10, 10))(10)(10)(10)
      child1 = reproduce(starvingCreature)
      child2 = reproduce(ateCreature)
    } yield {
      println("OINOI")
      assert(child1.isEmpty)
      assert(child2.isEmpty)
    }
  }

  test("A ReproducingCreature should reproduce") {
    for {
      reproducingCreature <- IOCreature.makeIOReproducingCreature(Position(10, 10))(10)(10)(10)
      child <- IO.sync(reproduce(reproducingCreature))
    } yield {
      assert(child.nonEmpty)
    }
  }

  test("The new creature should be a StarvingCreature") {
    for {
      reproducingCreature <- IOCreature.makeIOReproducingCreature(Position(10, 10))(10)(10)(10)
      child <- IO.now(reproduce(reproducingCreature))
    } yield for {
      c <- child
    } yield
      {
      assert(
        c match {
          case StarvingCreature(_, _, _, _) => true
          case _ => false
        }
      )
    }

  }

  test("A StarvingCreature should not survive") {
    for {
      starvingCreature <- IOCreature.makeIOStarvingCreature(Position(10, 10))(10)(10)(10)
      survive <- IO.now(survive(starvingCreature))
    } yield {
      assert(!survive)
    }
  }

  test("A AteCreature and a ReproducingCreature should survive") {
    for {
      ateCreature <- IOCreature.makeIOAteCreature(Position(10, 10))(10)(10)(10)
      reproducingCreature <- IOCreature.makeIOReproducingCreature(Position(10, 10))(10)(10)(10)
      survive1 <- IO.now(survive(ateCreature))
      survive2 <- IO.now(survive(reproducingCreature))
    } yield {
      assert(survive1)
      assert(survive2)
    }
  }

}