package helpers.json

import model.Environment
import model.entity.Creature.Creature
import model.entity.{AteCreature, Food, ReproducingCreature, StarvingCreature}
import model.output.Output.Output
import play.api.libs.json.{JsObject, Json}

object PimpModelJson {

  import helpers.json.JsonLabel._

  implicit class OutputToJson(output: Output) {
    def outputToJson: JsObject = {
      def toJson(out: Output): JsObject = out.keySet.toList match {
        case day :: _ => toJson(out - day) ++ Json.obj(day.toString -> out(day).environmentToJson)
        case Nil => Json.obj()
      }
      toJson(output)
    }
  }

  implicit class EnvironmentToJson(environment: Environment) {
    def creaturesToJson: JsObject = Json.obj(CREATURES -> environment.creatures.map(_.creatureToJson))
    def foodsToJson: JsObject = Json.obj(FOOD -> environment.food.map(_.foodToJson))
    def environmentToJson: JsObject = Json.obj(ENVIRONMENT -> (creaturesToJson ++ foodsToJson))
  }

  implicit class FoodToJson(f: Food) {
    def xJson: JsObject = Json.obj(X -> f.center.x)
    def yJson: JsObject = Json.obj(Y -> f.center.y)
    def positionJson: JsObject = Json.obj(POSITION -> (xJson ++ yJson))
    def foodToJson: JsObject = Json.obj(FOOD -> positionJson)
  }

  implicit class CreatureToJson(c: Creature) {

    def condition: JsObject = {
      val cond: String = c match {
        case StarvingCreature(_,_,_,_) => STARVING
        case AteCreature(_,_,_,_) => ATE
        case ReproducingCreature(_,_,_,_) => REPRODUCING
      }
      Json.obj(CONDITION -> cond)
    }

    def xJson: JsObject = Json.obj(X -> c.center.x)
    def yJson: JsObject = Json.obj(Y -> c.center.y)
    def positionToJson: JsObject = Json.obj(POSITION -> (xJson ++ yJson))

    def sizeJson: JsObject = Json.obj(SIZE -> c.radius)
    def speedJson: JsObject = Json.obj(SPEED -> c.speed)

    def creatureToJson: JsObject = Json.obj(CREATURE -> (condition ++ sizeJson ++ speedJson ++ positionToJson))
  }

}