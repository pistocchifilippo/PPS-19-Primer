package model.creature.movement

import model.creature.Creature
import model.{Blob, Position}

trait MovingCreature extends Creature with Movement

case class MStarvingCreature(
                             override val center: Position,
                             override val speed: Double,
                             override val energy: Double,
                             override val radius: Double,
                             override val goal: Blob
                           ) extends MovingCreature

case class MAteCreature(
                        override val center: Position,
                        override val speed: Double,
                        override val energy: Double,
                        override val radius: Double,
                        override val goal: Blob
                      ) extends MovingCreature

case class MReproducingCreature(
                                override val center: Position,
                                override val speed: Double,
                                override val energy: Double,
                                override val radius: Double,
                                override val goal: Blob
                              ) extends MovingCreature


