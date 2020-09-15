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
    parameters <- View.buildWithIO
    controller <- IO.now(
      parameters match {
        case Some(params) => Option(Controller(params.view)(params.nDays, params.nCreatures, params.nFood))
        case _ => Option.empty
    })
    _ <- IO.now(
      controller match {
        case Some(contr) => Option(contr.execute())
        case _ => Option.empty
      }
    )
    //stats <- c.execute
    //_ <- IO.sync(view.print("stats"))
    _ <- IO.sleep(Duration(15, SECONDS))
  } yield ()

  override def run(args: List[String]): IO[ioeffect.Void, Application.ExitStatus] =
    runApplication.attempt.map(_.fold(_ => 1, _ => 0)).map(ExitStatus.ExitNow(_))

}