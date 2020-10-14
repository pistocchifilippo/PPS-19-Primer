import cats.effect.IO
import view.View
import controller.Controller

object Application extends App {

  val application: IO[Unit] = for {
    parameters <- View.collectParameters
    sim <- Controller.makeSimulation(parameters)
    output <- Controller.execute(sim)
    _ <- View.print(parameters.view, output)
  } yield ()

  application.unsafeRunSync()

}