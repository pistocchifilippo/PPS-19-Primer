package controller.simulator.iterator

import cats.effect.IO

trait PureIterator [A <: PureIterator[A]] {
  def next(): IO[A]
  def hasNext: Boolean

  def executeAll: IO[A] = foldRight(this.asInstanceOf[A])((a,_) => a)
//  {
//    def executeAll(self: A): IO[A] = for {
//      next <- self.next()
//      d <- if(next.hasNext) executeAll(next) else IO pure self
//    } yield d
//    executeAll(this.asInstanceOf[A])
//  }

  def foldRight[B](base: B)(f: (A, B) => B): IO[B] = {
    def foldRight(self: A)(base: B)(f: (A, B) => B): IO[B] = for {
      next <- if (self.hasNext) self.next() else IO pure self
      d <- if (self.hasNext) foldRight(next)(f(next, base))(f) else IO pure base
    } yield d
    foldRight(this.asInstanceOf[A])(base)(f)
  }

}
