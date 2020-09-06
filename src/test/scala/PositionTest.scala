import model.{Boundaries, Position}
import org.scalatest.funsuite.AnyFunSuite

class PositionTest extends AnyFunSuite {

  import model.Position._
  val boundaries: Boundaries = Boundaries(Position(0,0), Position(100,100))

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
