package helpers.json

import model.{Boundaries, Environment, Position}
import model.entity.Creature.Creature
import model.entity.{AteCreature, Food, ReproducingCreature, StarvingCreature}
import model.output.Output.Output
import play.api.libs.json.{JsObject, Json, Writes}

object PimpModelJson {

//  implicit class environmentToJson(environment: Environment) {
//    def creaturesJson: JsObject = Json.obj("Creatures" -> environment.creatures.map(_.toJson))
////    def toJson: JsObject = ???
//  }

  implicit class CreatureToJson(c: Creature) {
    def condition: JsObject = {
      val condition: String = c match {
        case StarvingCreature(_,_,_,_) => "Starving"
        case AteCreature(_,_,_,_) => "Ate"
        case ReproducingCreature(_,_,_,_) => "Reproducing"
      }
      Json.obj("Condition" -> condition)
    }
    def sizeJson: JsObject = Json.obj("Size" -> c.radius)
    def speedJson: JsObject = Json.obj("Speed" -> c.speed)
    def xJson: JsObject = Json.obj("X" -> c.center.x)
    def yJson: JsObject = Json.obj("Y" -> c.center.y)

    def toJson: JsObject = Json.obj("Creature" -> (condition ++ sizeJson ++ speedJson ++ xJson ++ yJson))
  }

}

object TestFun extends App {

  import helpers.json.PimpModelJson._

  val c = StarvingCreature(Position(10,10), 10, 10, 10)

  val out: Output = Map.empty
  val food = Traversable(Food(Position(10,10), 10))
  val creatures = Traversable(StarvingCreature(Position(10,10), 10, 10, 10),AteCreature(Position(10,10), 11, 11, 1))
  val environment1 = Environment(Boundaries(Position(10,10), Position(10,10)), food, creatures)

  println(
    c.toJson
    )

//  println(
//    environment1.creaturesJson
//  )


}
