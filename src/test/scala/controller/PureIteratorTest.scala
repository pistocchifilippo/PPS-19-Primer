package controller

import cats.effect.IO
import controller.simulator.DaySimulator
import org.scalatest.funsuite.AnyFunSuite
import testsUtil.Mock._

class PureIteratorTest extends AnyFunSuite {

  val simulator: DaySimulator = mockDaySimulator

  test("Folding result should be as the given"){
    val test: IO[Unit] = for {
      sim <- IO pure simulator
      nStep <- sim.foldRight(0)((_,v) => v+1)
    } yield {
      assert(nStep equals MOCK_STEP)
    }
    test.unsafeRunSync()
  }

  test("Should be the last element"){
    val test: IO[Unit] = for {
      sim <- IO pure simulator
      last <- sim.executeAll
    } yield {
      assert(!last.hasNext)
    }
    test.unsafeRunSync()
  }

}
