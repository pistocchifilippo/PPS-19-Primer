package model

import org.scalatest.funsuite.AnyFunSuite

class BlobTest extends AnyFunSuite{

  test("Test blob collisions"){
    val b1 = BlobImplementation(3.0 -> 3.0, 2)
    val b2 = BlobImplementation(4.0 -> 4.0, 2)

    val b3 =  BlobImplementation(30.0 -> 30.0, 2)
    val b4 =  BlobImplementation(4.0 -> 4.0, 2)

    assert(Blob.collide(b1)(b2))

    assert(!Blob.collide(b3)(b4))
  }

  test("Test blob bounds collisions"){
    val bounds = Boundaries(topLeft = 0.0 -> 0.0, bottomRight =  100.0 -> 100.0)
    val b1 = BlobImplementation(5.0 -> 5.0,10)
    val b2 = BlobImplementation(50.0 -> 50.0, 1)
    assert(Blob.collideBoundary(b1)(bounds))
    assert(!Blob.collideBoundary(b2)(bounds))
  }


}
