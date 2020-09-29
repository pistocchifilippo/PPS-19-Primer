package model

import model.environment.Boundaries._
import model.environment.Boundaries
import org.scalatest.funsuite.AnyFunSuite

class PositionTest extends AnyFunSuite {

  import model.environment.Position._
  val boundaries: Boundaries = Boundaries(0.0 -> 0.0, 100.0 -> 100.0)
  val boundaries2: Boundaries = Boundaries(10.0-> 10.0, 99.0 -> 98.0)

  test("Should all be inside bounds"){

    assert(
      (for { _ <- 0 to 100 } yield RandomPosition(boundaries))
        .forall(p => isInside(boundaries, p)))

    assert(
      (for { _ <- 0 to 100 } yield RandomPosition(boundaries2))
        .forall(pos => isInside(boundaries2, pos)))
  }

  test("Should be on boundaries") {
      for {
        p <-
          for {
            _ <- 0 to 5
          } yield RandomEdgePosition(boundaries)
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
