package controller.simulator.iterator

import cats.effect.IO
import controller.simulator.Simulator

trait PureIterator [A <: Simulator] {
  def next(): IO[A]
  def hasNext: Boolean
}
