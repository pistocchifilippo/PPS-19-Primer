package controller.simulator.iterator

import cats.effect.IO

//[A<:PureIterator[A]]
trait PureIterator [A] {

  def elem: IO[A]

  def next: IO[PureIterator[A]]

  def hasNext: Boolean

  def executeAll: IO[PureIterator[A]] = {
    def executeAll(self: PureIterator[A]): IO[PureIterator[A]] = for {
      next <- self.next
      v <- if (self.hasNext) executeAll(next) else IO pure self
    } yield v
    executeAll(this)
  }

  def foldRight[B](base: B)(f: (PureIterator[A], B) => B): IO[B] = {
    def foldRight(self: PureIterator[A])(base: B)(f: (PureIterator[A], B) => B): IO[B] =  for {
      next <- self.next
      v <- if (self.hasNext) foldRight(next)(f(next, base))(f) else IO pure base
    } yield v
    foldRight(this)(base)(f)
  }

}
