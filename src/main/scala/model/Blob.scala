package model

trait Blob {
  def center : Position
  def radius : Double
}

trait BlobImplementation extends Blob{
  override def center: Position = center
  override def radius: Double = radius
}

case class Creature(override val center : Position, override val radius : Double ) extends BlobImplementation {}
case class Food(override val center : Position, override val radius : Double ) extends BlobImplementation {}

object Blob{

  //def collide(b1: model.Blob)(b2: model.Blob)(sense: (model.Blob, model.Blob) => Boolean) : Boolean = {
  def collide(blob1: Blob)(blob2: Blob) : Boolean = {
    def distance(pos1: Position, pos2: Position): Double = {
      val dx = pos1.x - pos2.x
      val dy = pos1.y - pos2.y
      Math.sqrt(dx*dx + dy*dy)
    }
    distance(blob1.center, blob2.center) < (blob1.radius + blob2.radius)
    }

  def collideBoundary(blob: Blob)(bounds: Boundaries) : Boolean = {
    blob.center.x - blob.radius < bounds.topLeft.x ||
    blob.center.x + blob.radius > bounds.bottomRight.x ||
    blob.center.y - blob.radius < bounds.topLeft.y ||
    blob.center.y + blob.radius > bounds.bottomRight.y
  }
}

