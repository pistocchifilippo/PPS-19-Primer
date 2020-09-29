package model.environment

import model.environment.Blob.Blob
import model.environment.Position.Position

case class Food(
               center: Position,
               radius: Double
               ) extends Blob
