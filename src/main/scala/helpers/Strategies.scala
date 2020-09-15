package helpers

import java.io.{File, FileWriter}

import com.sun.xml.internal.ws.developer.Serialization
import helpers.Configurations.{BOUNDARIES, CREATURES_ENERGY, CREATURES_RADIUS, CREATURES_SPEED, FOOD_RADIUS}
import javax.swing.JFrame
import model.Blob.makeBlobCollection
import model.creature.{Creature, StarvingCreature}
import model.{Boundaries, Environment, Food, Position}
import scalaz.ioeffect.IO
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
  def printFile(s: String): Unit = {
    for {
      f <- IO.now(new File("hello.json"))
      w <- IO.now(new FileWriter(f))
      _ <- IO.now({
        w.write(s)
        w.close()
      })
    } yield ()
  }

  def update(environment: Environment, jframe: Option[JFrame]): Option[Visualizer] = {
    println("update call")
    jframe match {
      case Some(frame) => {
        frame.getContentPane.removeAll()
        val visualizer = Visualizer(environment)
        frame.getContentPane.add(visualizer)
        Option(visualizer)
      }
      case _ => Option.empty
    }
  }

  def getFrame(bool: Boolean): Option[JFrame] = bool match {
    case _ if bool => Option(View.buildFrame())
    case _ => Option.empty
  }
}
