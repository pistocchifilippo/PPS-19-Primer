import model.entity.Creature.Creature
import model.{Boundaries, Position}
import model.Position._
import model.Boundaries._
import org.scalatest.funsuite.AnyFunSuite
import model.entity._
import org.scalatest.flatspec.AnyFlatSpec

import scala.util.Random

class FactoryTest extends AnyFunSuite {

  val bounds : Boundaries = Boundaries(Position(0,0), Position(100, 100))

  val foodstrategy : () => Position = () => randomPosition(bounds)
  val creaturestrategy : () => Position = () => randomEdgePosition(bounds)

  val foodSet: Set[Food] = Food(units = 100, radius = 10)(foodstrategy)
  val creatureSet: Set[Creature] = Creature.makeSet(100, 10, 10, 10)(creaturestrategy)

  test("Food coordinates should all be between boundaries"){
    assert(foodSet.forall(f => isInside(bounds, f.center)))
  }

  test("Creature coordinates should all be on border"){
    assert(creatureSet.forall(f => isOnBorder(bounds, f.center)))
  }

  test("The factory should return a Set of given size"){
    assert(Food(100, 10)(foodstrategy).size == 100)
    assert(Creature.makeSet(100, 10, 10, 10)(creaturestrategy).size == 100)
  }


}
