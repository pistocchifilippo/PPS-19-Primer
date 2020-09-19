package helpers

import java.io._

import helpers.Configurations._
import javax.swing.JFrame
import model.Blob.makeBlobCollection
import model.Position._
import model.creature.movement.StarvingCreature
import model.output.Output
import model.output.Output.Output
import model._
import model.creature.movement.EnvironmentCreature.EnvironmentCreature
import view.{SimulationView, View, Visualizer}

import cats.effect.IO

object Strategies {

//  def randomPosition(boundaries: Boundaries): () => Position =
//    () => Position(Random.nextInt(boundaries.bottomRight.x.toInt),
//      Random.nextInt(boundaries.bottomRight.y.toInt))

  def randomBoundedPosition: Position = Position.RandomPosition(BOUNDARIES)
  def randomBoundedEdgePosition: Position = Position.RandomEdgePosition(BOUNDARIES)

//  def makeBoundedFoodCollection(nFood: Int): Traversable[Food] = makeBlobCollection(() => Food(randomBoundedPosition, FOOD_RADIUS))(nFood)
//  def makeOnBoundsCreaturesCollection(nCreature: Int): Traversable[Creature] = makeBlobCollection(() => StarvingCreature(randomBoundedEdgePosition, CREATURES_SPEED, CREATURES_ENERGY, CREATURES_RADIUS))(nCreature)

  def randomGoal: Blob = BlobImplementation(randomBoundedPosition, GOAL_RADIUS)

  def makeBoundedFoodCollection(nFood: Int): Traversable[Food] = makeBlobCollection(() => Food(RandomPosition(BOUNDARIES), FOOD_RADIUS))(nFood)
  def makeOnBoundsCreaturesCollection(nCreature: Int): Traversable[EnvironmentCreature] = makeBlobCollection(() => StarvingCreature(RandomEdgePosition(BOUNDARIES), CREATURES_SPEED, CREATURES_ENERGY, CREATURES_RADIUS, randomGoal))(nCreature)

  def printCLI(output: Output): IO[Unit] = putStrLn(Output.CliParser(output))

  def printFile(output: Output): IO[Unit] = for {
      file <- IO(new File("hello.json"))
      w <- IO(new FileWriter(file))
      _ <- IO(w.write(Output.JsonParser(output)))
      _ <- IO(w.close())
    } yield ()


  val updateJFrame: (Environment, Option[JFrame]) => () => Unit = (environment, jframe) => () => {
    jframe match {
    case Some(frame) => {
      Thread.sleep(UPDATE_TIME_MS)
      frame.getContentPane.removeAll()
      val visualizer = Visualizer(environment)
      frame.getContentPane.add(visualizer)
      frame.revalidate()
    }
    case _ => {}
  }}




  //def updateFrame(environment: Environment, jframe: Option[JFrame]): () => Unit = updateJFrame(environment, jframe)

  def getFrame(bool: Boolean): Option[JFrame] = bool match {
    case _ if bool => Option(SimulationView.buildFrame())
    case _ => Option.empty
  }

  def putStrLn(str: String): IO[Unit] = cats.effect.IO(println(str))
  def getStrLn: IO[String] = cats.effect.IO(scala.io.StdIn.readLine())

}
