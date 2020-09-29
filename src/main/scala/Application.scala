import cats.effect.IO
import helpers.io.IoConversion._
import controller.Controller
import view.View._
import controller.Controller._

object Application extends App {

  val application: IO[Unit] = for {
    parameters <- collectParameters
    sim <- makeSimulation(parameters)
    output <- Controller.execute(sim)
    _ <- parameters.view.print(output)
  } yield ()

  application.unsafeRunSync()

}