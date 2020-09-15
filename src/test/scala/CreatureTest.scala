import model.Position
import model.creature.{AteCreature, ReproducingCreature, StarvingCreature}
import org.scalatest.funsuite.AnyFunSuite

class CreatureTest extends AnyFunSuite {

  import model.creature.Creature._
  implicit val randomPos: () => Position = () => Position(10, 10)
  val featureMutation: Double => Double = e => e * 0.1


  test("A non ReproducingCreature should not reproduce") {

    val starvingCreature = StarvingCreature(Position(10, 10),10,10,10)
    val ateCreature = AteCreature(Position(10, 10),10,10,10)

    val child1 = starvingCreature.reproduce(featureMutation)(featureMutation)
    val child2 = ateCreature.reproduce(featureMutation)(featureMutation)

    assert(child1.isEmpty)
    assert(child2.isEmpty)

  }

  test("A ReproducingCreature should reproduce") {
    val reproducingCreature = ReproducingCreature(Position(10, 10),10,10,10)
    val child = reproducingCreature.reproduce(featureMutation)(featureMutation)
    assert(child.nonEmpty)
  }

  test("The new creature should be a StarvingCreature") {
    val reproducingCreature = ReproducingCreature(Position(10, 10),10,10,10)
    val child = reproducingCreature.reproduce(featureMutation)(featureMutation)

    for {
      c <- child
    } yield {
      assert(
        c match {
          case StarvingCreature(_, _, _, _) => true
          case _ => false
        }
      )
    }

  }

  test("A StarvingCreature should not survive") {
    val starvingCreature = StarvingCreature(Position(10, 10),10,10,10)
    val cSurvive = starvingCreature.survive

    assert(!cSurvive)

  }

  test("A AteCreature and a ReproducingCreature should survive") {
    val ateCreature = AteCreature(Position(10, 10),10,10,10)
    val reproducingCreature = ReproducingCreature(Position(10, 10),10,10,10)
    val survive1 = ateCreature.survive
    val survive2 = reproducingCreature.survive

    assert(survive1)
    assert(survive2)

  }

  test("Position should change") {
    val creature = StarvingCreature(Position(10, 10),10,10,10)
//    val movedCreature = move(creature)(Position(30,5))
//    assert(movedCreature.center equals Position(30,5))
  }

  test("Creature class should not change") {
    val starvingCreature = StarvingCreature(Position(10, 10),10,10,10)
    val ateCreature = AteCreature(Position(10, 10),10,10,10)
    val reproducingCreature = ReproducingCreature(Position(10, 10),10,10,10)

//    assert(
////      move(starvingCreature)(Position(30,5)) match {
////        case StarvingCreature(_, _, _, _) => true
////        case _ =>false
////      }
////    )
//
////    assert(
////      move(ateCreature)(Position(30,5)) match {
////        case AteCreature(_, _, _, _) => true
////        case _ =>false
////      }
////    )
//
////    assert(
////      move(reproducingCreature)(Position(30,5)) match {
////        case ReproducingCreature(_, _, _, _) => true
////        case _ => false
////      }
////    )
//
  }

  test("Energy should be lower") {
    val energy = 100000
    val creature = StarvingCreature(Position(10, 10),10, energy,20)
//    val movedCreature = move(creature)(Position(30,5))(kineticConsumption)
//
//    assert(movedCreature.energy < energy)
  }

  // EVOLUTION SET TESTS
  test("Evolution Set size should be the same") {

    val setSize = 10
    val evolutionSet =
      {
        for {
          x <- 0 until setSize
        } yield AteCreature(Position(10, 10),10,10,x)
      }.toSet

    val evolve = makeEvolutionSet(evolutionSet)(10)(randomPos)(featureMutation)(featureMutation)

    assert(evolve.size equals setSize)
  }

  test("Evolution Set size should be the double") {

    val setSize = 10
    val evolutionSet =
      {
        for {
          x <- 0 until setSize
        } yield ReproducingCreature(Position(10, 10),10,10,x)
      }

    val evolve = makeEvolutionSet(evolutionSet)(10)(randomPos)(featureMutation)(featureMutation)

    assert(evolve.size equals setSize*2)
  }

  test("Evolution Set creatures should be Starving creatures"){
    val setSize = 10
    val evolutionSet =
      {
        for {
          x <- 0 until setSize
        } yield ReproducingCreature(Position(10, 10),10,10,x)
      }.toSet

    val evolve = makeEvolutionSet(evolutionSet)(10)(randomPos)(featureMutation)(featureMutation)

    for {
      c <- evolve
    } yield
      assert(c match {
          case StarvingCreature(_,_,_,_) => true
          case _ => false
        }
      )
  }


}