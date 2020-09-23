package helpers

import java.io._

import helpers.Configurations._
import javax.swing.JFrame
import model.Blob.{FoodCreatureCollision, makeBlobCollection}
import model.Position._
import model.creature.movement.StarvingCreature
import model.output.Output
import model.output.Output.Output
import model._
import model.creature.movement.EnvironmentCreature.EnvironmentCreature
import view.{SimulationView, View, Visualizer}
import helpers.io.IoConversion._
import cats.effect.IO

object Strategies {

  // IO
  def putStrLn(str: String): IO[Unit] = cats.effect.IO(println(str))
  def getStrLn: IO[String] = cats.effect.IO(scala.io.StdIn.readLine())

  // Positions
  def randomBoundedPosition: Position = Position.RandomPosition(BOUNDARIES)
  def randomBoundedEdgePosition: Position = Position.RandomEdgePosition(BOUNDARIES)

  def makeBoundedFoodCollection(nFood: Int): Traversable[Food] = makeBlobCollection(() => Food(RandomPosition(BOUNDARIES), FOOD_RADIUS))(nFood)
  def makeOnBoundsCreaturesCollection(nCreature: Int): Traversable[EnvironmentCreature] =
    makeBlobCollection(() => StarvingCreature(RandomEdgePosition(BOUNDARIES), CREATURES_SPEED, CREATURES_ENERGY, CREATURES_RADIUS, randomGoal))(nCreature)

  def randomGoal: Blob = BlobImplementation(randomBoundedPosition, GOAL_RADIUS)

  // Collisions
  def collidingCreatures(collisions: Traversable[FoodCreatureCollision]): List[EnvironmentCreature] = collisions.map{_._1}.toList
  def collidingFood(collisions: Traversable[FoodCreatureCollision]): List[Food] = collisions.map{_._2}.toList

  // View
  def printCLI(output: Output): IO[Unit] = putStrLn(Output.CliParser(output))

  def printFile(output: Output): IO[Unit] = for {
      file <- IO(new File("hello.json"))
      w <- IO(new FileWriter(file))
      _ <- IO(w.write(Output.JsonParser(output)))
      _ <- IO(w.close())
    } yield ()


  val updateJFrame: (Environment, Option[JFrame]) => () => Unit = (environment, jFrame) => () => {

    def _update(frame: JFrame): IO[Unit] = for {
      _ <- IO{Thread.sleep(UPDATE_TIME_MS)}
      _ <- IO{frame.getContentPane.removeAll()}
      visualizer <- Visualizer(environment)
      _ <- IO{frame.getContentPane.add(visualizer)}
      _ <- IO{frame.revalidate()}
    } yield()

    jFrame match {
      case Some(frame) => _update(frame).unsafeRunSync()
      case _ =>
    }
  }

  //def updateFrame(environment: Environment, jframe: Option[JFrame]): () => Unit = updateJFrame(environment, jframe)

  def getFrame(bool: Boolean): Option[JFrame] = bool match {
    case _ if bool => Option(SimulationView.buildFrame())
    case _ => Option.empty
  }

}
