package view.gets

import cats.effect.IO
import helpers.Strategies.{getStrLn, putStrLn}

object Gets {

  /** A Get is a request of a parameter to the user */
  type Get = String => IO[String]

  /** A GetScheduler keep asking the request until the input is correct */
  type GetScheduler = (() => IO[String], String => Boolean) => IO[String]

  def getParameters: Get = request => for {
    _ <- putStrLn(request)
    in <- getStrLn
  } yield in


  def scheduleGet: GetScheduler = (get, accept) => for {
    in <- get()
    res <- if (accept(in)) IO{in} else scheduleGet(get, accept)
  } yield res

}
