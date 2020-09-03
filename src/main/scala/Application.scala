import model.Position
import model.entity.IO.IOCreature

object Application extends App {
  println("Welcome to natural selection simulator!!!")

  for {
    c <- IOCreature.makeIOStarvingCreature(Position(10,10))(10)(10)(10)
  } yield println(c)
}