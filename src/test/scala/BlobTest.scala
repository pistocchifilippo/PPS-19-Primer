import org.scalatest.funsuite.AnyFunSuite

class BlobTest extends AnyFunSuite{

  test("Test collisioni tra blob"){
    assert(Blob.collide(Creature(new Position(3, 3), 2))(Creature(new Position(4, 4), 2)))
    assert(!Blob.collide(Creature(new Position(30, 30), 2))(Food(new Position(4, 4), 2)))
  }

  test("Test collisioni tra blob e bounds"){
    val bounds = new Boundaries(topLeft = new Position(0,0), bottomRight =  new Position(100, 100))
    val blob1 = Creature(new Position(5, 5), 10)
    val blob2 = Creature(new Position(50, 50), 1)
    assert(Blob.collideBoundary(blob1)(bounds))
    assert(!Blob.collideBoundary(blob2)(bounds))
  }


}
