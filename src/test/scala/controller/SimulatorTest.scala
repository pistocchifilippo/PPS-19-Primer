package controller

import cats.effect.IO
import controller.simulator.{DaySimulator, DayStepSimulator}
import helpers.Configurations.{BOUNDARIES, FIRST_DAY}
import helpers.Strategies._
import model.environment.Environment
import model.environment.Environment._
import org.scalatest.funsuite.AnyFunSuite
import view.utils.ViewUtils._
import view.graphic.BaseView

class SimulatorTest extends AnyFunSuite{

  val env: Environment = Environment(BOUNDARIES, makeBoundedFoodCollection(100), makeOnBoundsCreaturesCollection(50))
  val view: BaseView = BaseView(CLIPrinter)(Option.empty)
  val nDays = 20

  test("A day simulator should have expected behaviour" ) {

    val test: IO[Unit] = for {
      sim <- IO pure DaySimulator(FIRST_DAY, 100, nDays, env, view)
      sim1 <- IO pure DayStepSimulator(FIRST_DAY, env, view)
      end <- sim.executeAll
      end1 <- sim1.executeAll
    } yield {
      assert(sim.hasNext equals true)
      assert(sim1.hasNext equals true)
      assert(end.hasNext equals false)
      assert(end1.hasNext equals false)
    }

    test.unsafeRunSync()
  }

}
