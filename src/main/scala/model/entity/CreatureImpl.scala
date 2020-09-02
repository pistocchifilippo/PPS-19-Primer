package model.entity

import model.Position

case class StarvingCreature(
                           override val speed: Double,
                           override val energy: Double,
                           override val center: Position,
                           override val radius: Double
                           ) extends Creature

case class AteCreature(
                      override val speed: Double,
                      override val energy: Double,
                      override val center: Position,
                      override val radius: Double
                      ) extends Creature

case class ReproducingCreature(
                              override val speed: Double,
                              override val energy: Double,
                              override val center: Position,
                              override val radius: Double
                              ) extends Creature