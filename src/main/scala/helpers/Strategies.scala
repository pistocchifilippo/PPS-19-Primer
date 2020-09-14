package helpers

import helpers.Configurations.{BOUNDARIES, CREATURES_ENERGY, CREATURES_RADIUS, CREATURES_SPEED, FOOD_RADIUS}
import javax.swing.JFrame
import model.Blob.makeBlobCollection
import model.entity.Creature.Creature
import model.entity.{Food, StarvingCreature}
import model.{Boundaries, Environment, Position}
import view.{View, Visualizer}

import scala.util.Random

object Strategies {

//  def randomPosition(boundaries: Boundaries): () => Position =
//    () => Position(Random.nextInt(boundaries.bottomRight.x.toInt),
//      Random.nextInt(boundaries.bottomRight.y.toInt))

  val randomBoundedPosition: Position = Position.randomPosition(BOUNDARIES)
  val randomBoundedEdgePosition: Position = Position.randomEdgePosition(BOUNDARIES)

  def makeBoundedFoodCollection(nFood: Int): Traversable[Food] = makeBlobCollection(() => Food(randomBoundedPosition, FOOD_RADIUS))(nFood)
  def makeOnBoundsCreaturesCollection(nCreature: Int): Traversable[Creature] = makeBlobCollection(() => StarvingCreature(randomBoundedEdgePosition, CREATURES_SPEED, CREATURES_ENERGY, CREATURES_RADIUS))(nCreature)

  def printCLI(s: String): Unit = println(s)
  def printFile(s: String): Unit = println(s)

  def none(environment: Environment, frame: JFrame) {}

  def update(environment: Environment, frame: JFrame): Option[Visualizer] = {
    println("Update")
    frame.getContentPane.removeAll()
    frame.getContentPane.add(Visualizer(environment))
    Option(Visualizer(environment))
  }

  /*
  def update(bool: Boolean, environment: Environment) = {
    println("Update")
    Option(Visualizer(environment))
  }
   */

  def getFrame(bool: Boolean): Option[JFrame] = bool match {
    case _ if bool => Option(View.buildFrame())
    //case _ if bool => Option(View.buildFrame(Environment(BOUNDARIES, Traversable(), Traversable())))
    case _ => Option.empty
  }
}
