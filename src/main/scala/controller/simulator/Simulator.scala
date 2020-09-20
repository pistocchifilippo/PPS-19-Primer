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

  def executeAll: IO[Simulator] = {
    @scala.annotation.tailrec
    def consume(simulator: IO[Simulator]): IO[Simulator] = if (hasNext) consume(next) else IO.pure{this}
    consume(IO.pure{this})
  }

}