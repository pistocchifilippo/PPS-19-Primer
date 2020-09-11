import java.io.IOException

import controller.Controller
import model.{Environment, Position}
import model.entity.IO.{IOCreature, IOFood}
import scalaz.ioeffect.{IO, SafeApp}
import view.View
import controller.Controller._
import scalaz.ioeffect

import scala.util.Random

object Application extends SafeApp {

  def runApplication: IO[IOException, Unit] =

  for {
    view <- View.buildWithIO
    params <- View.collectSimulationParameters

    //environment <- Environment
    //c <- IO.now(Controller(view)) //auto build per env
    //stats <- c.execute
    //_ <- IO.sync(view.print("stats"))
  } yield ()

  override def run(args: List[String]): IO[ioeffect.Void, Application.ExitStatus] =
    runApplication.attempt.map(_.fold(_ => 1, _ => 0)).map(ExitStatus.ExitNow(_))

}