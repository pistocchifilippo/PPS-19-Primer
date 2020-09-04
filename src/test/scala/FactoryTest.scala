import model.entity.Creature.Creature
import model.{Boundaries, Position}
import org.scalatest.funsuite.AnyFunSuite
import model.entity._

import scala.util.Random

class FactoryTest extends AnyFunSuite{

  val f: Food = Food(Position(0,0), 100)
  val bounds : Boundaries = Boundaries(Position(0,0), Position(100, 100))
  val strategy : () => Position = () => Position(Random.nextInt(bounds.bottomRight.x.toInt), Random.nextInt(bounds.bottomRight.x.toInt))
  val foodSet: Set[Food] = Food(100, 10)(strategy)

//  val creatureSet: Set[Creature] = Creature.makeSet(100, 10, 10, 10)(strategy)

  test("The factory should return a Set of given size"){
    assert(foodSet.size == 100)
//    assert(creatureSet.size == 100)
  }

  test("The coords should all be between boundaries"){
    assert(foodSet.count(f => f.center.x >= 0 && f.center.x <= 100 && f.center.y >= 0 && f.center.y <= 100) == 100)
  }

}
