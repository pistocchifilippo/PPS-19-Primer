import model.Position
import model.entity.IO.{IOCreature, IOFood}

import scala.util.Random

object Application extends App {
  println("Welcome to natural selection simulator!!!")

  for {
    c <- IOCreature.makeIOStarvingCreature(Position(10,10))(10)(10)(10)
    //f <- IOFood.makeIOFood(Position(10,10))(10)
    //s <- IOFood.makeIOFoodSet(100)(10)(() => Position(new Random().nextInt(100), new Random().nextInt(100)))
  } yield println(c)
}