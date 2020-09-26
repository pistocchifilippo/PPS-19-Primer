package model

import model.Boundaries._
import org.scalatest.funsuite.AnyFunSuite

class PositionTest extends AnyFunSuite {

  import model.Position._
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
          p._1 == 0 ||
          p._1 == 100 ||
          p._2 == 0 ||
          p._2 == 100
        )
      }
  }

}
