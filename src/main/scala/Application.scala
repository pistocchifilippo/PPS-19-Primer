import model.{Environment, Position}
import model.entity.IO.{IOCreature, IOFood}
import scalaz.ioeffect.IO
import view.View

import scala.util.Random

object Application extends App {
  println("Welcome to natural selection simulator!!!")

  for {
    view <- View.buildWithIO
    params <- View.collectSimulationParameters
    //environment <- Environment

    //c <- Controller(view) //auto build per env
    //stats <- c.execute
    //_ <- IO.sync(view.print("stats"))
  } yield ()
}