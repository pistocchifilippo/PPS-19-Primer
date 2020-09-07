import model.Boundaries
import model.Boundaries._
import model.Position
import org.scalatest.funsuite.AnyFunSuite

class PositionTest extends AnyFunSuite {

  import model.Position._
  val boundaries: Boundaries = Boundaries(Position(0,0), Position(100,100))
  val boundaries2: Boundaries = Boundaries(Position(10, 10), Position(99,88))

  test("Should all be inside bounds"){

    assert(
      (for { _ <- 0 to 100 } yield randomPosition(boundaries))
        .forall(p => isInside(boundaries, p)))

    assert(
      (for { _ <- 0 to 100 } yield randomPosition(boundaries2))
        .forall(pos => isInside(boundaries2, pos)))
  }

  test("Should be on boundaries") {
      for {
        p <-
          for {
            _ <- 0 to 5
          } yield randomEdgePosition(boundaries)
      } yield {
        println(p)
        assert(
          p.x == 0 ||
          p.x == 100 ||
          p.y == 0 ||
          p.y == 100
        )
      }
  }

}
