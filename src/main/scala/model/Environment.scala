package model

import java.io.IOException

import model.entity._
import model.entity.Creature._
import scalaz.ioeffect.IO
import scalaz.ioeffect.console.{getStrLn, putStrLn}

trait BlobEnvironment {
  def boundaries : Boundaries
  def food : Traversable[Food]
  def creatures : Traversable[Creature]
}

case class Environment( override val boundaries: Boundaries,
                        override val food: Traversable[Food],
                        override val creatures : Traversable[Creature]) extends BlobEnvironment

object Environment {

  //def makeStep(): Environment = ???

}