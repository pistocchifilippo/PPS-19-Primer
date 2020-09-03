package model.entity.IO

import model.Position
import model.entity.Creature.Creature
import model.entity.{AteCreature, ReproducingCreature, StarvingCreature}
import scalaz.ioeffect.IO

object IOCreature {
  def makeIOStarvingCreature(center: Position)(r: Double)(energy: Double)(speed: Double): IO[Void, Creature] = IO.now(
    StarvingCreature(
      center = center,
      radius = r,
      speed = speed,
      energy = energy
    )
  )

  def makeIOAteCreature(center: Position)(r: Double)(energy: Double)(speed: Double): IO[Void, Creature] = IO.now(
    AteCreature(
      center = center,
      radius = r,
      speed = speed,
      energy = energy
    )
  )

  def makeIOReproducingCreature(center: Position)(r: Double)(energy: Double)(speed: Double): IO[Void, Creature] = IO.now(
    ReproducingCreature(
      center = center,
      radius = r,
      speed = speed,
      energy = energy
    )
  )
}