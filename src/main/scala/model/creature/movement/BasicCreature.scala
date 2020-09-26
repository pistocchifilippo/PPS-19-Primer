package model.creature.movement

import model.Blob
import model.Position.Position
import model.creature.movement.EnvironmentCreature.EnvironmentCreature

/** This is a creature that ate no food
 *
 * @param center of the creature
 * @param speed of the creature
 * @param energy of the creature
 * @param radius of the creature
 * @param goal of the creature
 */
case class StarvingCreature(
                             override val center: Position,
                             override val speed: Double,
                             override val energy: Double,
                             override val radius: Double,
                             override val goal: Blob
                           ) extends EnvironmentCreature

/** This is a creature that ate one food
 *
 * @param center of the creature
 * @param speed of the creature
 * @param energy of the creature
 * @param radius of the creature
 * @param goal of the creature
 */
case class AteCreature(
                        override val center: Position,
                        override val speed: Double,
                        override val energy: Double,
                        override val radius: Double,
                        override val goal: Blob
                      ) extends EnvironmentCreature

/** This is a creature that ate two food
 *
 * @param center of the creature
 * @param speed of the creature
 * @param energy of the creature
 * @param radius of the creature
 * @param goal of the creature
 */
case class ReproducingCreature(
                                override val center: Position,
                                override val speed: Double,
                                override val energy: Double,
                                override val radius: Double,
                                override val goal: Blob
                              ) extends EnvironmentCreature