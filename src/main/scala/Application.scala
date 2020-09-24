import cats.effect.IO
import controller.ApplicationController
import model.io.ModelFunctionalities._
import view.io.ViewFunctionalities._


object Application extends App {

  val application: IO[Unit] = for {
    parameters <- collectParameters
    sim <- makeSimulation(parameters)
    output <- ApplicationController.execute(sim)
    _ <- parameters.view.print(output)
  } yield ()

  application.unsafeRunSync()

}