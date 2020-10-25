package controller

import cats.effect.IO
import controller.simulator.DaySimulator
import org.scalatest.funsuite.AnyFunSuite
import testsUtil.Mock._

class FunctionalIteratorTest extends AnyFunSuite {

  val simulator: DaySimulator = mockDaySimulator

  test("Folding result should be as the given") {
    val test: IO[Unit] = for {
      sim <- IO pure simulator
      nStep <- sim.foldRight(0)((sim, v) => sim match {
        case _: DaySimulator => v + 1
        case _ => v
      })
    } yield {
      assert(nStep equals MOCK_STEP)
    }

    test.unsafeRunSync()
  }

}
