package model

import model.Boundaries._
import model.creature.Creature
import org.scalatest.funsuite.AnyFunSuite

class FactoryTest extends AnyFunSuite {

  import helpers.Configurations._
  import helpers.Strategies._

  val bounds: Boundaries = BOUNDARIES

  val foodSet: Traversable[Food] = makeBoundedFoodCollection(100)
  val creatureSet: Traversable[Creature] = makeOnBoundsCreaturesCollection(100)

  test("Food coordinates should all be between boundaries"){
    assert(foodSet.forall(f => isInside(bounds, f.center)))
  }

  test("Creature coordinates should all be on border"){
    assert(creatureSet.forall(f => isOnBorder(bounds, f.center)))
  }

  test("The factory should return a Set of given size"){
    assert(foodSet.size == 100)
    assert(creatureSet.size == 100)
  }

}
