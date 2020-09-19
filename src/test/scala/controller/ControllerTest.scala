package controller

import controller.simulator.DaySimulator
import helpers.Configurations.BOUNDARIES
import helpers.Strategies._
import model.Environment
import model.output.Output
import org.scalatest.funsuite.AnyFunSuite
import view.View

class ControllerTest extends AnyFunSuite{

  val env = Environment(BOUNDARIES, makeBoundedFoodCollection(100), makeOnBoundsCreaturesCollection(50))
  val view = View(printCLI)(getFrame(false))

  test("A controller should return an Output of proper size" ) {
    val sim = DaySimulator(0, 100, 20, env, view)
    assert(ApplicationController().execute(sim)(Output()).size == 20)
    val sim2 = DaySimulator(0, 100, 1, env, view)
    assert(ApplicationController().execute(sim2)(Output()).size == 1)
    val sim3 = DaySimulator(0, 100, 0, env, view)
    assert(ApplicationController().execute(sim3)(Output()).isEmpty)
  }

}
