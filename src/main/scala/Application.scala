import cats.effect.IO
import controller.ApplicationController
import controller.simulator.DaySimulator
import helpers.Configurations._
import helpers.Strategies._
import model.Environment
import model.output.Output
import view.SimulationView

object Application extends App {

  def application: IO[Unit] = for {
    parameters <- SimulationView.buildWithIO
    _ <- parameters match {
      case Some(param) => for {
        environment <- IO(Environment(BOUNDARIES, makeBoundedFoodCollection(param.nFood), makeOnBoundsCreaturesCollection(param.nCreatures)))
        simulator <- IO(DaySimulator(0, param.nFood, param.nDays, environment, param.view))
        controller <- IO(ApplicationController())
        output <- IO(controller.execute(simulator)(Output())) // da aggiornare -broken-
        _ <- parameters.get.view.print(output)
      } yield ()
      // spostare la stampa lato view?
      case _ => putStrLn("Parametri non corretti. Riprova.")
    }
  } yield ()

  application.unsafeRunSync()

}