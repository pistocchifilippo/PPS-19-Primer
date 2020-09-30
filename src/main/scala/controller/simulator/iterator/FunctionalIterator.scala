package controller.simulator.iterator

import cats.effect.IO

/** This is a functional implementation of an Iterator
 *
 * This element has no side effect on its instance
 * The execution of the next routine does not affect the state of the object
 * The next function produce a new instance of [[FunctionalIterator]]
 *
 * @tparam A is bounded to be at least a [[FunctionalIterator]]
 */
trait FunctionalIterator [A <: FunctionalIterator[A]] {

  /** Does not mutate the state of the object
   *
   * @return An object of type IO[A] where A is the following instance
   */
  def next(): IO[A]

  /**
   *
   * @return true if can be done another next call
   */
  def hasNext: Boolean

  /** Consume all the iterator, until hasNext
   *
   * @return The last next call of the chain
   */
  def executeAll: IO[A] = foldRight(this.asInstanceOf[A])((a,_) => a)

  /**
   *
   * @param base the base case
   * @param f aggregation function
   * @tparam B the output type
   * @return the folded result
   */
  def foldRight[B](base: B)(f: (A, B) => B): IO[B] = {
    def foldRight(self: A)(base: B)(f: (A, B) => B): IO[B] = for {
      next <- if (self.hasNext) self.next() else IO pure self
      d <- if (self.hasNext) foldRight(next)(f(next, base))(f) else IO pure base
    } yield d
    foldRight(this.asInstanceOf[A])(base)(f)
  }

}
