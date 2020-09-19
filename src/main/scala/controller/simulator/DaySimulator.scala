package controller.simulator

import helpers.Configurations.BOUNDARIES
import helpers.Strategies.{makeBoundedFoodCollection, randomBoundedPosition}
import model.Environment
import model.creature.movement.EnvironmentCreature
import view.{SimulationView, View}

/** The day simulator execute an entire day per step */
case class DaySimulator(executedStep: Int,
                        nFood: Int,
                        nDays: Int,
                        environment: Environment,
                        view: SimulationView
                       ) extends Simulator {

  private val sizeMutation = EnvironmentCreature.noSizeMutation
  private val speedMutation = EnvironmentCreature.noSpeedMutation

  /**
   *
   * @return true if the simulator can do another step, simulating the following day
   */
  override def hasNext: Boolean = nDays > 0

  /** Executes a an entire day of the simulation
   *
   * @return A new simulator (maybe) ready to simulate another entire day
   */
  override def next(): Simulator = {
    println("[DAY " + nDays + " ]")

    val food = makeBoundedFoodCollection(nFood)
    val dayStepSim = DayStepSimulator(1, environment, view)
    val endDaySim = consumeDay(dayStepSim)
    val endCreatures = endDaySim.environment.creatures
    val creatures = EnvironmentCreature.makeEvolutionSet(endCreatures)(() => randomBoundedPosition)(sizeMutation)(speedMutation)
    val env = Environment(BOUNDARIES, food, creatures)

    DaySimulator(
      executedStep + 1,
      nFood,
      nDays - 1,
      env,
      view
    )
  }

  @scala.annotation.tailrec
  private def consumeDay(dayStepSimulator: Simulator): Simulator = if (dayStepSimulator.hasNext) consumeDay(dayStepSimulator.next()) else dayStepSimulator

}
