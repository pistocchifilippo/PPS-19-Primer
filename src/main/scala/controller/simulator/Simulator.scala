package controller.simulator

import helpers.Configurations._
import helpers.Strategies._
import model.creature.movement.{AteCreature, MovingCreature, ReproducingCreature, StarvingCreature}
import model.{Blob, Environment}
import view.View


trait Simulator extends Iterator [Simulator] {
  def environment: Environment
  def executedStep: Int
}

case class DayStepSimulator(executedStep: Int, environment: Environment, view: View) extends Simulator {

  implicit val kineticConsumption: (Double, Double) => Double =  MovingCreature.kineticConsumption

  override def hasNext: Boolean = environment.creatures.count(_.energy > 0) > 0

  override def next(): Simulator =  {

    val creatures = environment.creatures
    val food = environment.food

    val step = for {
      c <- creatures
      f <- food
      if Blob.collide(c)(f) && {c match {
        case ReproducingCreature(_, _, _, _, _) => false
        case _ => true}
      }
      newC <- creatures collect {
        case StarvingCreature(center, speed, energy, radius, goal) => AteCreature(center, speed, energy, radius, goal)
        case AteCreature(center, speed, energy, radius, goal) => ReproducingCreature(center, speed, energy, radius, goal)
        //case ReproducingCreature(center, speed, energy, radius, goal) => ReproducingCreature(center, speed, energy, radius, goal)
      }
      newF <- food filter (_ equals f)
    } yield (newF, newC)

    val newF = step map (_._1)
    val newC = step map (_._2)

    val env = Environment(BOUNDARIES, newF, newC)

    println("New Step")
    Thread.sleep(2000)
    view.update(environment, view.frame)

    DayStepSimulator(
      executedStep + 1,
      env,
      view
    )
  }

}

case class DaySimulator(executedStep: Int,
                         nFood: Int,
                         nDays: Int,
                         environment: Environment,
                        view: View
                         ) extends Simulator {

  private val sizeMutation = MovingCreature.noSizeMutation
  private val speedMutation = MovingCreature.noSpeedMutation

  override def hasNext: Boolean = nDays > 0

  override def next(): Simulator = {
    println("[DAY " + nDays + " ]")

    val food = makeBoundedFoodCollection(nFood)
    val dayStepSim = DayStepSimulator(1, environment, view)
    val endDaySim = consumeDay(dayStepSim)
    val endCreatures = endDaySim.environment.creatures
    val creatures = MovingCreature.makeEvolutionSet(endCreatures)(() => randomBoundedPosition)(sizeMutation)(speedMutation)
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

//class GUIDayStepSimulator(environment: Environment, view: View) extends DayStepSimulator(environment, view) with SimulationController


object prova extends App {
  val env = Environment(BOUNDARIES, makeBoundedFoodCollection(100), makeOnBoundsCreaturesCollection(50))
  val view = View(printCLI)(update)(getFrame(false))
  val s = DayStepSimulator(0, env, view)
  val sim = DaySimulator(0, 100, 20, env, view)
  sim.next()
}