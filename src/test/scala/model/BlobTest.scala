package model

import cats.effect.IO
import helpers.io.IoConversion._
import model.environment.{Blob, Boundaries}
import org.scalatest.funsuite.AnyFunSuite

class BlobTest extends AnyFunSuite {

  test("Test blob collisions") {

    val test: IO[Unit] = for {
      b1 <- Blob(3.0 -> 3.0, 2)
      b2 <- Blob(4.0 -> 4.0, 2)
      b3 <- Blob(30.0 -> 30.0, 2)
      b4 <- Blob(4.0 -> 4.0, 2)
    } yield {
      assert(Blob.collide(b1)(b2))
      assert(!Blob.collide(b3)(b4))
    }

    test.unsafeRunSync()
  }

  test("Test blob bounds collisions") {

    val test: IO[Unit] = for {
      bounds <- Boundaries(0.0 -> 0.0, 100.0 -> 100.0)
      b1 <- Blob(5.0 -> 5.0, 10)
      b2 <- Blob(50.0 -> 50.0, 1)
    } yield {
      assert(Blob.collideBoundary(b1)(bounds))
      assert(!Blob.collideBoundary(b2)(bounds))
    }

    test.unsafeRunSync()
  }


}
