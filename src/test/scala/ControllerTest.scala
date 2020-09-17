import controller.ApplicationController
import controller.simulator.{DaySimulator, DayStepSimulator}
import helpers.Configurations.BOUNDARIES
import helpers.Strategies.{getFrame, makeBoundedFoodCollection, makeOnBoundsCreaturesCollection, printCLI, update}
import model.Environment
import org.scalatest.funsuite.AnyFunSuite
import view.View

class ControllerTest extends AnyFunSuite{

  val env = Environment(BOUNDARIES, makeBoundedFoodCollection(100), makeOnBoundsCreaturesCollection(50))
  val view = View(printCLI)(update)(getFrame(false))

  test("A controller should return an Output of proper size" ) {
    val sim = DaySimulator(0, 100, 20, env, view)
    assert(ApplicationController().execute(sim).size == 20)
    val sim2 = DaySimulator(0, 100, 1, env, view)
    assert(ApplicationController().execute(sim2).size == 1)
    val sim3 = DaySimulator(0, 100, 0, env, view)
    assert(ApplicationController().execute(sim3).isEmpty)
  }

}
