package controller.simulator

import helpers.Configurations.BOUNDARIES
import model.{Blob, Environment}
import model.creature.movement.{EnvironmentCreature, ReproducingCreature}
import view.View

case class DayStepSimulator(executedStep: Int, environment: Environment, view: View) extends Simulator {

  implicit val kineticConsumption: (Double, Double) => Double =  EnvironmentCreature.kineticConsumption

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
    val newF = food filter (!collisionsFood.contains(_))

    // the new creature set
    // implement feed trait into creature
    val newC = creatures collect {
      case c if collisionsCreature.contains(c) => EnvironmentCreature.feed(c)
      case c => c
    }

    val env = Environment(BOUNDARIES, newF, newC)

    //    Thread.sleep(300)
    view.update(environment, view.frame)

    DayStepSimulator(
      executedStep + 1,
      env,
      view
    )
  }

}
