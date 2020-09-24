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
//    _ <- parameters match {
//      case Some(param) => for {
//        sim <- makeSimulation(param)
//        output <- ApplicationController.execute(sim)
//        _ <- param.view.print(output)
//      } yield ()
//      // spostare la stampa lato view?
//      case _ => putStrLn("Parametri non corretti. Riprova.")
//    }
  } yield ()

  application.unsafeRunSync()

}