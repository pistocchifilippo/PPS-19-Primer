package model.output

import model.Environment
import play.api.libs.json.Json._

object Output {

  type Output = Map[Int, Environment]

  def apply(): Output = Map.empty
  def apply(output: Output): Output = output
  def log(output: Output)(day: Int, environment: Environment): Output = output + (day -> environment)

  // Parsing strategy factories
  private type Parser = Output => String

  import helpers.json.PimpModelJson._

  object JsonParser extends Parser {
    override def apply(out: Output): String = out.outputToJson.toString()
  }

  object CliParser extends Parser {
    override def apply(out: Output): String = prettyPrint(out.outputToJson)
  }

}
