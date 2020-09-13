package model.output

import model.{Boundaries, Environment, Position}
import model.entity.{Food, StarvingCreature}
import play.api.libs.json.{JsObject, Json}

object Output {

  type Output = Map[Int, Environment]

  def log(output: Output)(day: Int, environment: Environment): Output = output + (day -> environment)


  private type Parser = Output => String

  object JsonParser extends Parser {

    import helpers.json.PimpModelJson._

    override def apply(out: Output): String = {

      def makeJson(output: Output): JsObject = output.keySet.toList match {
        case day :: _ => makeJson(output - day) ++ Json.obj(day.toString -> out(day).environmentToJson)
        case Nil => Json.obj()
      }

      makeJson(out).toString
    }
  }

  object CliParser extends Parser {
    override def apply(out: Output): String = ???
  }

}
