import cats.effect.IO
import controller.ApplicationController
import helpers.Strategies._
import model.io.Transitions._
import view.SimulationView

object Application extends App {

  def application: IO[Unit] = for {
    parameters <- SimulationView.collectParameters
    _ <- parameters match {
      case Some(param) => for {
        sim <- makeSimulation(param)
        output <- ApplicationController.execute(sim)
        _ <- parameters.get.view.print(output)
      } yield ()
      // spostare la stampa lato view?
      case _ => putStrLn("Parametri non corretti. Riprova.")
    }
  } yield ()

  application.unsafeRunSync()

}