import model.{Environment, Position}
import model.entity.IO.{IOCreature, IOFood}
import view.View

import scala.util.Random

object Application extends App {
  println("Welcome to natural selection simulator!!!")

  for {
    p <- View.collectParameters
    //c <- Controller(p)
    //stats <- c.execute
    //_ <- view.print(stats)
  } yield ()
}