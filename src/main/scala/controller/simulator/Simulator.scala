package controller.simulator

import cats.effect.IO
import helpers.Configurations.BOUNDARIES
import helpers.Strategies.{getFrame, makeBoundedFoodCollection, makeOnBoundsCreaturesCollection, printCLI}
import model.Environment
import model.output.Output
import model.output.Output.log
import view.View

/** This trait represent the behaviour of the Simulator
 *
 * A simulator extends Iterator behaviour
 *
 * A simulator embeds the environment to work with it
 * A simulator have a number of executed step
 */
trait Simulator extends Iterator [IO[Simulator]] {
  /**
   *
   * @return the environment of the step
   */
  def environment: Environment

  /**
   *
   * @return the number of executed step
   */
  def executedStep: Int

  def executeAll: IO[Simulator] = {
    def executeAll(dayStepSimulator: Simulator): IO[Simulator] = for {
      next <- dayStepSimulator.next()
      d <- if(next.hasNext) executeAll(next) else IO pure dayStepSimulator
    } yield d
    executeAll(this)
  }

  def foldRight[A](base: A)(f: (Simulator, A) => A): IO[A] = {
    def foldRight(simulator: Simulator)(base: A)(f: (Simulator, A) => A): IO[A] =  for {
      next <- simulator.next()
      d <- if (simulator.hasNext) foldRight(next)(f(next, base))(f) else IO pure base
    } yield d
    foldRight(this)(base)(f)
  }

}