package controller

import controller.simulator.{DaySimulator, Simulator}
import helpers.Configurations.BOUNDARIES
import helpers.Strategies._
import model.Environment
import org.scalatest.funsuite.AnyFunSuite
import view.View

class SimulatorTest extends AnyFunSuite{

  val env: Environment = Environment(BOUNDARIES, makeBoundedFoodCollection(100), makeOnBoundsCreaturesCollection(50))
  val view: View = View(printCLI)(getFrame(false))
  val nDays = 20

  test("A day simulator should have expected behaviour" ) {
    var sim: Simulator = DaySimulator(0, 100, nDays, env, view)
    for(_ <- 1 to nDays){
      assert(sim.hasNext)
      sim = sim.next()
    }
    assert(!sim.hasNext)
  }

}
