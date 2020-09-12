import java.io.IOException

import controller.Controller
import helpers.Configurations._
import model.Environment
import scalaz.ioeffect
import scalaz.ioeffect.{IO, SafeApp}
import view.View

import scala.concurrent.duration.{Duration, SECONDS}

object Application extends SafeApp {

  def runApplication: IO[IOException, Unit] =

  for {
    view <- View.buildWithIO
    params <- View.collectSimulationParameters
    c <- IO.now(Controller(view.get)(params._1, params._2, params._3)) //auto build per env
    _ <- IO.now(c.execute())
    //stats <- c.execute
    //_ <- IO.sync(view.print("stats"))
    _ <- IO.sleep(Duration(15, SECONDS))
  } yield ()

  override def run(args: List[String]): IO[ioeffect.Void, Application.ExitStatus] =
    runApplication.attempt.map(_.fold(_ => 1, _ => 0)).map(ExitStatus.ExitNow(_))

}