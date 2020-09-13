package model.output

import model.{Boundaries, Environment, Position}
import model.entity.{Food, StarvingCreature}
import play.api.libs.json.{JsObject, Json}

object Output {

  type Output = Map[Int, Environment]

  def log(output: Output)(day: Int, environment: Environment): Output = output + (day -> environment)


  private type Parser = Output => String

  object JsonParser extends Parser {
    override def apply(out: Output): String = {

      def makeJson(output: Output): JsObject = output.keySet.toList match {
        case h :: _ => makeJson(output - h) ++ Json.obj(h.toString -> "env")
        case Nil => Json.obj()
      }

      makeJson(out).toString
    }
  }

  object CliParser extends Parser {
    override def apply(out: Output): String = ???
  }

}

object Test extends App {
  import Output._
  val out: Output = Map.empty
  val food = Traversable(Food(Position(10,10), 10))
  val creatures = Traversable(StarvingCreature(Position(10,10), 10, 10, 10))
  val environment1 = Environment(Boundaries(Position(10,10), Position(10,10)), food, creatures)
  val environment2 = Environment(Boundaries(Position(10,10), Position(10,10)), food.tail, creatures.tail)

  val newOut1 = Output.log(out)(1, environment1)
  val newOut2 = Output.log(newOut1)(2, environment2)

  println(
    JsonParser(newOut2)
  )


}
