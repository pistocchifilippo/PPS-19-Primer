package helpers.json

import model.{Environment, Position}
import model.entity.Creature.Creature
import model.entity.StarvingCreature
import play.api.libs.json.{JsObject, Json, Writes}

object PimpModelJson {

//  implicit class environmentToJson(environment: Environment) {
//    def toJson: JsObject = ???
//  }

  implicit class CreatureToJson(c: Creature) {
//    def condition: JsObject = Json.obj("Condition" -> "")
    def sizeJson: JsObject = Json.obj("Size" -> c.radius)
    def speedJson: JsObject = Json.obj("Speed" -> c.speed)
    def xJson: JsObject = Json.obj("X" -> c.center.x)
    def yJson: JsObject = Json.obj("Y" -> c.center.y)

    def toJson: JsObject = Json.obj("Creature" -> (sizeJson ++ speedJson ++ xJson ++ yJson))
  }

}

object TestFun extends App {

  import helpers.json.PimpModelJson._

  val c = StarvingCreature(Position(10,10), 10, 10, 10)

  println(
    c.toJson
  )


}
