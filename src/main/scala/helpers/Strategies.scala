package helpers

import java.io._

import helpers.Configurations._
import javax.swing.JFrame
import model.Blob.makeBlobCollection
import model.Position._
import model.creature.Creature
import model.creature.movement.{EnvironmentCreature, StarvingCreature}
import model.output.Output
import model.output.Output.Output
import model.{Blob, BlobImplementation, Environment, Food, Position}
import scalaz.ioeffect.IO
import scalaz.ioeffect.console._
import view.{View, Visualizer}

object Strategies {

//  def randomPosition(boundaries: Boundaries): () => Position =
//    () => Position(Random.nextInt(boundaries.bottomRight.x.toInt),
//      Random.nextInt(boundaries.bottomRight.y.toInt))

  def randomBoundedPosition: Position = Position.randomPosition(BOUNDARIES)
  def randomBoundedEdgePosition: Position = Position.randomEdgePosition(BOUNDARIES)

//  def makeBoundedFoodCollection(nFood: Int): Traversable[Food] = makeBlobCollection(() => Food(randomBoundedPosition, FOOD_RADIUS))(nFood)
//  def makeOnBoundsCreaturesCollection(nCreature: Int): Traversable[Creature] = makeBlobCollection(() => StarvingCreature(randomBoundedEdgePosition, CREATURES_SPEED, CREATURES_ENERGY, CREATURES_RADIUS))(nCreature)

  def randomGoal: Blob = BlobImplementation(randomBoundedPosition, GOAL_RADIUS)

  def makeBoundedFoodCollection(nFood: Int): Traversable[Food] = makeBlobCollection(() => Food(randomPosition(BOUNDARIES), FOOD_RADIUS))(nFood)
  def makeOnBoundsCreaturesCollection(nCreature: Int): Traversable[EnvironmentCreature] = makeBlobCollection(() => StarvingCreature(randomEdgePosition(BOUNDARIES), CREATURES_SPEED, CREATURES_ENERGY, CREATURES_RADIUS, randomGoal))(nCreature)

  def printCLI(output: Output): IO[IOException, Unit] = putStrLn(Output.CliParser(output))

  def printFile(output: Output): IO[IOException, Unit] = for {
      file <- IO.now(new File("hello.json"))
      w <- IO.now(new FileWriter(file))
      _ <- IO.sync(w.write(Output.JsonParser(output)))
      _ <- IO.now(w.close())
    } yield ()

  def update(environment: Environment, jframe: Option[JFrame]): Option[Visualizer] = {
    //println("update call")
    jframe match {
      case Some(frame) => {
        frame.getContentPane.removeAll()
        val visualizer = Visualizer(environment)
        frame.getContentPane.add(visualizer)
        frame.revalidate()
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
