package model.output

import model.environment.Environment._
import play.api.libs.json.JsObject
import play.api.libs.json.Json._

/** Module containing the output */
object Output {

  type Output = Map[Int, Environment]

  /** Creates an empty output
   *
   * @return an empty output
   */
  def apply(): Output = Map.empty

  /** Makes a new updated output
   *
   * @param output The old output
   * @param day of the simulation
   * @param environment resulting environment of the simulation
   * @return The updated output
   */
  def log(output: Output)(day: Int, environment: Environment): Output = output + (day -> environment)
  def lastDay(out: Output): Output = out.filter(_._1 equals out.keys.foldRight(0)((a, b) => if (a>b) a else b))
  def lastDayEnvironment(out: Output): Environment = lastDay(out).values.head

  /** Parsing strategy factories */
  private type Parser = Output => String

  import helpers.json.PimpModelJson._

  /** Strategy for json string */
  object JsonParser extends Parser {
    override def apply(out: Output): String = out.outputToJson.toString()
  }

  /** Strategy for Cli readable string */
  object CliParser extends Parser {
    override def apply(out: Output): String = "Survived Creatures: " + lastDayEnvironment(out).creatures.size
  }

  /** Strategy for Cli readable string */
  object PrettyJsonParser extends Parser {
    override def apply(out: Output): String = prettyPrint(out.outputToJson)
  }

  /** Strategy for last day stats in json string */
  object LastDayJsonParser extends Parser {
    override def apply(out: Output): String = prettyPrint {lastDay(out).outputToJson}
  }

}
