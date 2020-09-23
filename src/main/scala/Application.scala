import cats.effect.IO
import controller.ApplicationController
import controller.simulator.DaySimulator
import helpers.Configurations._
import helpers.Strategies._
import model.Environment
import view.SimulationView

object Application extends App {

  def application: IO[Unit] = for {
    parameters <- SimulationView.collectParameters
    _ <- parameters match {
      case Some(param) => for {
        environment <- IO {Environment(BOUNDARIES, makeBoundedFoodCollection(param.nFood), makeOnBoundsCreaturesCollection(param.nCreatures))}
        simulator <- IO{DaySimulator(FIRST_DAY, param.nFood, param.nDays, environment, param.view)}
        output <- ApplicationController.execute(simulator)
        _ <- parameters.get.view.print(output)
      } yield ()
      // spostare la stampa lato view?
      case _ => putStrLn("Parametri non corretti. Riprova.")
    }
  } yield ()

  application.unsafeRunSync()

}