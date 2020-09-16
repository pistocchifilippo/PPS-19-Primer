import java.io.IOException

import controller.ApplicationController
import controller.simulator.DaySimulator
import helpers.Configurations._
import helpers.Strategies._
import model.Environment
import scalaz.ioeffect
import scalaz.ioeffect.{IO, SafeApp}
import view.View

object Application extends SafeApp {

  def runApplication: IO[IOException, Unit] = for {
    // gestione parametri errati
    parameters <- View.buildWithIO
    environment <- IO.now(Environment(BOUNDARIES, makeBoundedFoodCollection(parameters.get.nFood), makeOnBoundsCreaturesCollection(parameters.get.nCreatures)))
    simulator <- IO.now(DaySimulator(0, parameters.get.nFood, parameters.get.nDays, environment, parameters.get.view))
    controller <- IO.now(ApplicationController())
    output <- IO.now(controller.execute(simulator)) // da aggiornare -broken-
    _ <- IO.sync(parameters.get.view.print(output))
  } yield ()

  override def run(args: List[String]): IO[ioeffect.Void, Application.ExitStatus] =
    runApplication.attempt.map(_.fold(_ => 1, _ => 0)).map(ExitStatus.ExitNow(_))

}