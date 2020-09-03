package model.entity.IO

import java.io.IOException

import model.Position
import model.entity.Creature.Creature
import model.entity.{AteCreature, ReproducingCreature, StarvingCreature}
import scalaz.ioeffect.IO

object IOCreature {
  def makeIOStarvingCreature(center: Position)(r: Double)(energy: Double)(speed: Double): IO[IOException, Creature] = IO.sync(
    StarvingCreature(
      center = center,
      radius = r,
      speed = speed,
      energy = energy
    )
  )

  def makeIOAteCreature(center: Position)(r: Double)(energy: Double)(speed: Double): IO[IOException, Creature] = IO.sync(
    AteCreature(
      center = center,
      radius = r,
      speed = speed,
      energy = energy
    )
  )

  def makeIOReproducingCreature(center: Position)(r: Double)(energy: Double)(speed: Double): IO[IOException, Creature] = IO.sync(
    ReproducingCreature(
      center = center,
      radius = r,
      speed = speed,
      energy = energy
    )
  )
}
