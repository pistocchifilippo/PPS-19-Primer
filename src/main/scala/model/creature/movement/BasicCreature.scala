package model.creature.movement

import model.{Blob, Position}

case class StarvingCreature(
                             override val center: Position,
                             override val speed: Double,
                             override val energy: Double,
                             override val radius: Double,
                             override val goal: Blob
                           ) extends MovingCreature

case class AteCreature(
                        override val center: Position,
                        override val speed: Double,
                        override val energy: Double,
                        override val radius: Double,
                        override val goal: Blob
                      ) extends MovingCreature

case class ReproducingCreature(
                                override val center: Position,
                                override val speed: Double,
                                override val energy: Double,
                                override val radius: Double,
                                override val goal: Blob
                              ) extends MovingCreature