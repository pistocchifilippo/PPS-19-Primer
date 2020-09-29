package model

import helpers.io.IoConversion._
import cats.effect.IO
import model.environment.{Blob, BlobImplementation, Boundaries}
import org.scalatest.funsuite.AnyFunSuite

class BlobTest extends AnyFunSuite{

  test("Test blob collisions"){

    val test: IO[Unit] = for {
        b1 <- BlobImplementation(3.0 -> 3.0, 2)
        b2 <- BlobImplementation(4.0 -> 4.0, 2)
        b3 <- BlobImplementation(30.0 -> 30.0, 2)
        b4 <- BlobImplementation(4.0 -> 4.0, 2)
    }yield {
      assert(Blob.collide(b1)(b2))
      assert(!Blob.collide(b3)(b4))
    }

    test.unsafeRunSync()
  }

  test("Test blob bounds collisions"){

    val test: IO[Unit] = for {
      bounds <- Boundaries(0.0 -> 0.0, 100.0 -> 100.0)
      b1 <- BlobImplementation(5.0 -> 5.0, 10)
      b2 <- BlobImplementation(50.0 -> 50.0, 1)
    } yield {
      assert(Blob.collideBoundary(b1)(bounds))
      assert(!Blob.collideBoundary(b2)(bounds))
    }

    test.unsafeRunSync()
  }


}
