import java.io.IOException

import controller.ApplicationController
import controller.simulator.DaySimulator
import helpers.Configurations._
import helpers.Strategies._
import model.Environment
import scalaz.ioeffect
import scalaz.ioeffect.console.putStrLn
import scalaz.ioeffect.{IO, SafeApp}
import view.View

object Application extends SafeApp {

  def runApplication: IO[IOException, Unit] = for {
    parameters <- View.buildWithIO
    _ <- parameters match {
      case Some(param) => for {
        environment <- IO.now(Environment(BOUNDARIES, makeBoundedFoodCollection(param.nFood), makeOnBoundsCreaturesCollection(param.nCreatures)))
        simulator <- IO.now(DaySimulator(0, param.nFood, param.nDays, environment, param.view))
        controller <- IO.now(ApplicationController())
        output <- IO.now(controller.execute(simulator)) // da aggiornare -broken-
        _ <- parameters.get.view.print(output)
      } yield ()
      // spostare la stampa lato view?
      case _ => putStrLn("Parametri non corretti. Riprova.")
    }
  } yield ()

  override def run(args: List[String]): IO[ioeffect.Void, Application.ExitStatus] =
    runApplication.attempt.map(_.fold(_ => 1, _ => 0)).map(ExitStatus.ExitNow(_))

}