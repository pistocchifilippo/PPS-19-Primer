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

    val creatures = environment.creatures map (_.move)

    val food = environment.food

    // collisions
    val collisions = for {
      c <- creatures
      f <- food
      if Blob.collide(c)(f) && {c match {
        case ReproducingCreature(_, _, _, _, _) => false
        case _ => true}
      }
    } yield (c, f)

    val collisionsCreature = collisions.map(_._1).toList
    val collisionsFood = collisions.map(_._2).toList

    // the new food set
    val newF = food.filter(!collisionsFood.contains(_))

    // the new creature set
    // implement feed trait into creature
    val newC = creatures.collect {
      case c: StarvingCreature if collisionsCreature.contains(c) => AteCreature(c.center, c.speed, c.energy, c.radius, c.goal)
      case c: AteCreature if collisionsCreature.contains(c) => ReproducingCreature(c.center, c.speed, c.energy, c.radius, c.goal)
      case c: MovingCreature => c
    }

    val env = Environment(BOUNDARIES, newF, newC)

    Thread.sleep(300)
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