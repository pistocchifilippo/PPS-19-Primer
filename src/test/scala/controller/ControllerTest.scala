package controller

import cats.effect.IO
import controller.simulator.DaySimulator
import helpers.Configurations.BOUNDARIES
import helpers.Strategies._
import model.Environment
import org.scalatest.funsuite.AnyFunSuite
import view.View
import view.io.ViewFunctionalities._

class ControllerTest extends AnyFunSuite{

  val env = Environment(BOUNDARIES, makeBoundedFoodCollection(100), makeOnBoundsCreaturesCollection(50))
  val view = View(printCLI)(Option.empty)

  test("A controller should return an Output of proper size" ) {

    val test: IO[Unit] = for {
      sim1 <- IO {DaySimulator(0, 100, 20, env, view)}
      sim2 <- IO {DaySimulator(0, 100, 1, env, view)}
      sim3 <- IO {DaySimulator(0, 100, 0, env, view)}
      out1 <- ApplicationController.execute(sim1)
      out2 <- ApplicationController.execute(sim2)
      out3 <- ApplicationController.execute(sim3)

    } yield {
      assert(out1.size == 20)
      assert(out2.size == 1)
      assert(out3.isEmpty)
    }

    test.unsafeRunSync()

  }

}
