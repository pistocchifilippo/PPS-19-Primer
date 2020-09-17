package model

import helpers.Strategies._
import model.creature.movement.{AteCreature, EnvironmentCreature, ReproducingCreature, StarvingCreature}
import org.scalatest.funsuite.AnyFunSuite

class CreatureTest extends AnyFunSuite {

  implicit val k: (Double, Double) => Double = EnvironmentCreature.kineticConsumption
  implicit val randomPos: () => Position = () => Position(10, 10)
  val dummyMutation: Double => Double = e => e * 0.1


  test("A non ReproducingCreature should not reproduce") {

    val starvingCreature = StarvingCreature(Position(10, 10),10,10,10, randomGoal)
    val ateCreature = AteCreature(Position(10, 10),10,10,10, randomGoal)

    val child1 = EnvironmentCreature.reproduce(starvingCreature)(dummyMutation)(dummyMutation)
    val child2 = EnvironmentCreature.reproduce(ateCreature)(dummyMutation)(dummyMutation)

    assert(child1.isEmpty)
    assert(child2.isEmpty)

  }

  test("A ReproducingCreature should reproduce") {
    val reproducingCreature = ReproducingCreature(Position(10, 10),10,10,10, randomGoal)
    val child = EnvironmentCreature.reproduce(reproducingCreature)(dummyMutation)(dummyMutation)
    assert(child.nonEmpty)
  }

  test("The new creature should be a StarvingCreature") {
    val reproducingCreature = ReproducingCreature(Position(10, 10),10,10,10, randomGoal)
    val child = EnvironmentCreature.reproduce(reproducingCreature)(dummyMutation)(dummyMutation)

    for {
      c <- child
    } yield {
      assert(
        c match {
          case StarvingCreature(_, _, _, _,_) => true
          case _ => false
        }
      )
    }

  }

  // Testing feed
  test("Creature should be feed") {
    val starvingCreature = StarvingCreature(Position(10, 10),10,10,10, randomGoal)
    val ateCreature = AteCreature(Position(10, 10),10,10,10, randomGoal)
    val reproducingCreature = ReproducingCreature(Position(10, 10),10,10,10, randomGoal)

    assert(
      EnvironmentCreature.feed(starvingCreature) match {
        case AteCreature(_, _, _, _, _) => true
        case _ => false
      }
    )

    assert(
      EnvironmentCreature.feed(ateCreature) match {
        case ReproducingCreature(_, _, _, _, _) => true
        case _ => false
      }
    )

    assert(
      EnvironmentCreature.feed(reproducingCreature) match {
        case ReproducingCreature(_, _, _, _, _) => true
        case _ => false
      }
    )
  }

  // Test move
  test("Position should change") {
    val creature = StarvingCreature(Position(10, 10),10,10,10, randomGoal)
    val movedCreature = creature.move(EnvironmentCreature.kineticConsumption)
    //assert(!{movedCreature.center equals creature.center})
  }

  test("Creature class should not change") {
    val starvingCreature = StarvingCreature(Position(10, 10),10,10,10, randomGoal)
    val ateCreature = AteCreature(Position(10, 10),10,10,10, randomGoal)
    val reproducingCreature = ReproducingCreature(Position(10, 10),10,10,10, randomGoal)

    assert(
      starvingCreature.move match {
        case StarvingCreature(_, _, _, _, _) => true
        case _ => false
      }
    )

    assert(
      ateCreature.move match {
        case AteCreature(_, _, _, _, _) => true
        case _ => false
      }
    )

    assert(
      reproducingCreature.move match {
        case ReproducingCreature(_, _, _, _, _) => true
        case _ => false
      }
    )

  }

  test("Energy should be lower") {
    val energy = 100000
    val creature = StarvingCreature(Position(10, 10),10, energy,20, randomGoal)
    val movedCreature = creature.move

    assert(movedCreature.energy < energy)
  }

  // EVOLUTION SET TESTS
  test("Evolution Set size should be the same") {

    val setSize = 10
    val evolutionSet =
      for {
        x <- 0 until setSize
      } yield AteCreature(Position(10, 10),10,10,x, randomGoal)

    val evolve = EnvironmentCreature.makeEvolutionSet(evolutionSet)(randomPos)(dummyMutation)(dummyMutation)

    assert(evolve.size equals setSize)
  }

  test("Evolution Set size should be the double") {

    val setSize = 10
    val evolutionSet =
      for {
        x <- 0 until setSize
      } yield ReproducingCreature(Position(10, 10),10,10,x, randomGoal)

    val evolve = EnvironmentCreature.makeEvolutionSet(evolutionSet)(randomPos)(dummyMutation)(dummyMutation)

    assert(evolve.size equals setSize * 2)
  }

  test("Evolution Set creatures should be Starving creatures"){
    val setSize = 10
    val evolutionSet =
      for {
        x <- 0 until setSize
      } yield ReproducingCreature(Position(10, 10),10,10,x, randomGoal)

    val evolve = EnvironmentCreature.makeEvolutionSet(evolutionSet)(randomPos)(dummyMutation)(dummyMutation)

    for {
      c <- evolve
    } yield
      assert(c match {
          case StarvingCreature(_,_,_,_,_) => true
          case _ => false
        }
      )
  }

}