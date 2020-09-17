package model

import org.scalatest.funsuite.AnyFunSuite

class BlobTest extends AnyFunSuite{

  test("Test blob collisions"){
    val b1 = BlobImplementation(Position(3, 3), 2)
    val b2 = BlobImplementation(Position(4, 4), 2)

    val b3 =  BlobImplementation(Position(30, 30), 2)
    val b4 =  BlobImplementation(Position(4, 4), 2)

    assert(Blob.collide(b1)(b2))

    assert(!Blob.collide(b3)(b4))
  }

  test("Test blob bounds collisions"){
    val bounds = Boundaries(topLeft = Position(0,0), bottomRight =  Position(100, 100))
    val b1 = BlobImplementation(Position(5, 5),10)
    val b2 = BlobImplementation(Position(50, 50),1)
    assert(Blob.collideBoundary(b1)(bounds))
    assert(!Blob.collideBoundary(b2)(bounds))
  }


}
