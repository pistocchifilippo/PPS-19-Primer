package model.creature

import model.Position

case class StarvingCreature(
                              override val center: Position,
                              override val speed: Double,
                              override val energy: Double,
                              override val radius: Double
                           ) extends Creature

case class AteCreature(
                        override val center: Position,
                        override val speed: Double,
                        override val energy: Double,
                        override val radius: Double
                      ) extends Creature

case class ReproducingCreature(
                                override val center: Position,
                                override val speed: Double,
                                override val energy: Double,
                                override val radius: Double
                              ) extends Creature