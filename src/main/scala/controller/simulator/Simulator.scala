package controller.simulator

import controller.simulator.iterator.FunctionalIterator
import model.environment.Environment.Environment

/** This trait represent the behaviour of the Simulator
 *
 * A simulator extends Iterator behaviour
 *
 * A simulator embeds the environment to work with it
 * A simulator have a number of executed step
 */
trait Simulator extends FunctionalIterator [Simulator] {

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

}