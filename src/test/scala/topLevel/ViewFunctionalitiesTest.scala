package topLevel

import java.io.File

import cats.effect.IO
import controller.simulator.DaySimulator
import helpers.Strategies.randomGoal
import model.creature.movement.EnvironmentCreature.StarvingCreature
import model.environment.Environment.Environment
import model.environment.{Boundaries, Environment, Food}
import model.output.Output
import model.output.Output.Output
import org.scalatest.funsuite.AnyFunSuite
import testsUtil.Mock._

class ViewFunctionalitiesTest extends AnyFunSuite{

  val out: Output = Map.empty
  val food = Traversable(Food(10.0-> 10.0, 10))
  val creatures = Traversable(StarvingCreature(10.0-> 10.0, 10, 10, 10, randomGoal))
  val environment: Environment = Environment(Boundaries(10.0-> 10.0, 10.0-> 10.0), food, creatures)


  test("A new stats file should be generated after a Run" ) {

    val simulator : DaySimulator = mockSimulatorFile

    def countFiles: Int ={
      new File("statistics").list().length
    }

    val test: IO[Unit] = for {
      n <- IO pure countFiles
      out <- IO pure Output.log(out)(1, environment)
      _ <- IO pure simulator.view.print(out)
    } yield assert(n+1 equals countFiles)

    test.unsafeRunSync()

  }

}
