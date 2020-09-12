package model.output

<<<<<<< HEAD
import model.Environment

trait Output {
  def out(): Map[Int, Environment]
}

object Output {

  def log(output: Output)(day: Int, environment: Environment): Output = () => output.out() + (day -> environment)

  private type Parser = Output => String

  object JsonParser extends Parser {
    override def apply(out: Output): String = ???
  }

  object CliParser extends Parser {
    override def apply(out: Output): String = ???

  }
=======
trait Output {
>>>>>>> dev-guimixin

}
