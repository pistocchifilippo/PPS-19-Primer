package helpers.io

import java.awt.Component
import java.io.{File, FileWriter}
import cats.effect.IO
import model.creature.movement.EnvironmentCreature.EnvironmentCreature
import model.environment.Blob.Blob
import model.environment.Boundaries
import model.environment.Environment._
import model.output.Output.Output
import view.graphic.Visualizer

/** Module for implicit conversions in [[IO]] elements, be used in for-comprehension statements */
object IoConversion {

  implicit def blobToIO(b: Blob): IO[Blob] = IO pure b
  implicit def boundsToIO(b: Boundaries): IO[Boundaries] = IO pure b
  implicit def creatureToIo(c: EnvironmentCreature): IO[EnvironmentCreature] = IO pure c
  implicit def outputToIo(out: Output): IO[Output] = IO pure out
  implicit def environmentToIo(env: Environment): IO[Environment] = IO pure env
  implicit def visualizerToIo(visualizer: Visualizer): IO[Visualizer] = IO.pure(visualizer)
  implicit def unitToIo(fun: Unit): IO[Unit] = IO.pure { () => fun }
  implicit def componentToIo(component: Component): IO[Component] = IO pure component
  implicit def fileToIo(file: File): IO[File] = IO pure file
  implicit def fileWriterToIo(fileWriter: FileWriter): IO[FileWriter] = IO pure fileWriter

}
