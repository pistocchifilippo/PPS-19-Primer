package model

import model.Position.{Position, MathPosition}
import model.creature.movement.EnvironmentCreature.EnvironmentCreature

/** Represent a generic Blob involved in the simulation */
trait Blob {
  def center : Position
  def radius : Double
}

/** Case class of a Blob */
case class BlobImplementation(override val center: Position,
                              override val radius: Double
                             ) extends Blob

object Blob {

  /** Creates a collection of Blob(s)
   *
   * @param producer represent the creation mode
   * @param units is the number of Blob(s) to be created
   *
   * @return a [[scala.collection.Traversable]] of Blob(s)
   * */
  def makeBlobCollection[A <: Blob](producer: () => A)(units: Int): Traversable[A] = if (units > 0) makeBlobCollection(producer)(units - 1) ++ Traversable(producer()) else Nil


  /**
   * Detect collisions between two Blobs
   *
   * @param blob1 is the first Blob
   * @param blob2 is the second Blob
   *
   * @return a [[Boolean]] which is `true` if the Blobs are colliding, `false` if they are not
   * */
  def collide(blob1: Blob)(blob2: Blob) : Boolean = (blob1.center distance blob2.center) < (blob1.radius + blob2.radius)

  /**
   * Detect collisions between a Blob and the Boundaries
   *
   * @param blob is the Blob
   *
   * @return a [[Boolean]] which is `true` if the Blob collides with boundaries, `false` otherwise
   * */
  def collideBoundary(blob: Blob)(bounds: Boundaries) : Boolean = {
    blob.center.x - blob.radius < bounds.topLeft.x ||
    blob.center.x + blob.radius > bounds.bottomRight.x ||
    blob.center.y - blob.radius < bounds.topLeft.y ||
    blob.center.y + blob.radius > bounds.bottomRight.y
  }

}

