package helpers.json

import model.creature.Creature
import model.creature.movement.EnvironmentCreature.{AteCreature, ReproducingCreature, StarvingCreature}
import model.environment.{Environment, Food}
import model.output.Output.Output
import play.api.libs.json.{JsObject, Json}

/** In this module there are Pimping for model library, in order to add json conversion */
object PimpModelJson {

  import helpers.json.JsonLabel._

  /** Adds following conversions to Output trait */
  implicit class OutputToJson(output: Output) {
    def outputToJson: JsObject = output.foldRight(Json.obj())((a,b) => b ++ Json.obj(a._1.toString -> a._2.environmentToJson))
  }

  /** Adds following conversions to Environment trait */
  implicit class EnvironmentToJson(environment: Environment) {
    def creaturesToJson: JsObject = Json.obj(CREATURES -> environment.creatures.map(_.creatureToJson))
    def foodsToJson: JsObject = Json.obj(FOOD -> environment.food.map(_.foodToJson))
    def environmentToJson: JsObject = Json.obj(ENVIRONMENT -> (creaturesToJson ++ foodsToJson))
  }

  /** Adds following conversions to Food trait */
  implicit class FoodToJson(f: Food) {
    def xJson: JsObject = Json.obj(X -> f.center.x)
    def yJson: JsObject = Json.obj(Y -> f.center.y)
    def positionJson: JsObject = Json.obj(POSITION -> (xJson ++ yJson))
    def foodToJson: JsObject = Json.obj(FOOD -> positionJson)
  }

  /** Adds following conversions to Creature trait */
  implicit class CreatureToJson(c: Creature) {

    def conditionJson: JsObject = {
      val cond: String = c match {
        case StarvingCreature(_,_,_,_,_) => STARVING
        case AteCreature(_,_,_,_,_) => ATE
        case ReproducingCreature(_,_,_,_,_) => REPRODUCING
      }
      Json.obj(CONDITION -> cond)
    }

    def xJson: JsObject = Json.obj(X -> c.center.x)
    def yJson: JsObject = Json.obj(Y -> c.center.y)
    def positionToJson: JsObject = Json.obj(POSITION -> (xJson ++ yJson))

    def sizeJson: JsObject = Json.obj(SIZE -> c.radius)
    def speedJson: JsObject = Json.obj(SPEED -> c.speed)

    def creatureToJson: JsObject = Json.obj(CREATURE -> (conditionJson ++ sizeJson ++ speedJson ++ positionToJson))
  }

}