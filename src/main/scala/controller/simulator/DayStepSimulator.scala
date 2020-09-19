package controller.simulator

import cats.effect.IO
import helpers.Configurations.BOUNDARIES
import model.{Blob, Environment}
import model.creature.movement.{EnvironmentCreature, ReproducingCreature}
import view.SimulationView

/** The DayStepSimulator represent the simulation for just one step of just one day */
case class DayStepSimulator(executedStep: Int, environment: Environment, view: SimulationView) extends Simulator {

  implicit val kineticConsumption: (Double, Double) => Double =  EnvironmentCreature.kineticConsumption

  /**
   *
   * @return true if the simulator can do another step
   */
  override def hasNext: Boolean = environment.creatures.count(_.energy > 0) > 0

  /** Executes a step of a day simulation
   *
   * @return A new simulator (maybe) ready to simulate another step of the day
   */
  override def next(): IO[Simulator] = for {
    creatures <- IO {environment.creatures map (_.move)}
    food <- IO {environment.food}

    // collisions
    collisions <- IO {for {
      c <- creatures
      f <- food
      if Blob.collide(c)(f) && {c match {
        case ReproducingCreature(_, _, _, _, _) => false
        case _ => true}
      }
    } yield (c, f)}

    collisionsCreature <- IO {collisions.map(_._1).toList}
    collisionsFood <- IO {collisions.map(_._2).toList}

    // the new food set
    newF <- IO {food filter (!collisionsFood.contains(_))}

    // the new creature set
    newC <- IO {creatures collect {
      case c if collisionsCreature.contains(c) => c.feed()
      case c => c
    }}

    env <- IO {Environment(BOUNDARIES, newF, newC)}
    _ <- IO {SimulationView.update(view, env)}

    } yield DayStepSimulator(
      executedStep + 1,
      env,
      view
    )





}
