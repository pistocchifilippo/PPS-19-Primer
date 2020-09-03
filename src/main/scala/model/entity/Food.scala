package model.entity

import model.{Blob, Position}

case class Food(
               center: Position,
               radius: Double
               ) extends Blob
