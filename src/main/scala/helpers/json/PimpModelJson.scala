package helpers.json

import model.creature.Creature
import model.creature.movement.EnvironmentCreature.{AteCreature, ReproducingCreature, StarvingCreature}
import model.environment.Environment.Environment
import model.environment.Food
import model.output.Output.Output
import play.api.libs.json.{JsObject, Json}

/** In this module there are Pimping for model library, in order to add json conversion */
object PimpModelJson {

  import helpers.json.JsonLabel._

  /** Adds following conversions to Output trait */
  implicit class OutputToJson(output: Output) {
    def outputToJson: JsObject = output.foldRight(Json.obj())((a, b) => b ++ Json.obj(a._1.toString -> a._2.environmentToJson))
  }

  /** Adds following conversions to Environment trait */
  implicit class EnvironmentToJson(environment: Environment) {
    def environmentToJson: JsObject = Json.obj(ENVIRONMENT -> (creaturesToJson ++ foodsToJson))

    def creaturesToJson: JsObject = Json.obj(CREATURES -> environment.creatures.map(_.creatureToJson))

    def foodsToJson: JsObject = Json.obj(FOOD -> environment.food.map(_.foodToJson))
  }

  /** Adds following conversions to Food trait */
  implicit class FoodToJson(f: Food) {
    def foodToJson: JsObject = Json.obj(FOOD -> positionJson)

    def positionJson: JsObject = Json.obj(POSITION -> (xJson ++ yJson))

    def xJson: JsObject = Json.obj(X -> f.center.x)

    def yJson: JsObject = Json.obj(Y -> f.center.y)
  }

  /** Adds following conversions to Creature trait */
  implicit class CreatureToJson(c: Creature) {

    def creatureToJson: JsObject = Json.obj(CREATURE -> (conditionJson ++ sizeJson ++ speedJson ++ positionToJson))

    def conditionJson: JsObject = {
      val cond: String = c match {
        case _: StarvingCreature => STARVING
        case _: AteCreature => ATE
        case _: ReproducingCreature => REPRODUCING
      }
      Json.obj(CONDITION -> cond)
    }

    def positionToJson: JsObject = Json.obj(POSITION -> (xJson ++ yJson))

    def xJson: JsObject = Json.obj(X -> c.center.x)

    def yJson: JsObject = Json.obj(Y -> c.center.y)

    def sizeJson: JsObject = Json.obj(SIZE -> c.radius)

    def speedJson: JsObject = Json.obj(SPEED -> c.speed)
  }

}