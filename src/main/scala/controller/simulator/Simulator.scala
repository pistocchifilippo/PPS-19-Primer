package controller.simulator

import cats.effect.IO
import model.Environment

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

  /** Execute all the simulation until hasNext
   *
   * @return The simulator executed as long as possible
   */
  def executeAll: IO[Simulator] = {
    def consume(dayStepSimulator: Simulator): IO[Simulator] =
      if (dayStepSimulator.hasNext) for {
        next <- dayStepSimulator.next()
        d <- consume(next)
      } yield d
      else IO{dayStepSimulator}
    consume(this)
  }

}