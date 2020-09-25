package view.io

import java.awt.BorderLayout
import java.io.{File, FileWriter}

import cats.effect.IO
import controller.simulator.{DaySimulator, Simulator}
import helpers.Configurations._
import helpers.Strategies.{getStrLn, isNumber, makeBoundedFoodCollection, makeOnBoundsCreaturesCollection, putStrLn}
import helpers.io.IoConversion._
import javax.swing.JFrame
import model.Environment
import model.output.Output
import model.output.Output.Output
import view.utils.ViewUtils.getFrame
import view.{SimulationParameters, SimulationView, View, Visualizer}

object ViewFunctionalities {

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


  def collectParameters : IO[SimulationParameters] = for {
    _ <- putStrLn(WELCOME)
    mode <- scheduleGet(() => getParameters(MODE), ACCEPT_MODE contains _)
    out <- scheduleGet(() => getParameters(OUT), ACCEPT_OUT contains _)
    nDays <- scheduleGet(() => getParameters(DAYS), isNumber)
    nCreatures <- scheduleGet(() => getParameters(CREATURES), isNumber)
    nFood <- scheduleGet(() => getParameters(FOOD), isNumber)
  } yield (mode, out, nDays.toInt, nCreatures.toInt, nFood.toInt) match {
    case ("1", "y", nDays,nCreatures,nFood) => SimulationParameters(View(printFile)(getFrame(false)), nDays, nCreatures, nFood)
    case ("1", "n", nDays,nCreatures,nFood) => SimulationParameters(View(printCLI)(getFrame(false)), nDays, nCreatures, nFood)
    case ("2", "y", nDays,nCreatures,nFood) => SimulationParameters(View(printFile)(getFrame(true)) , nDays, nCreatures, nFood)
    case ("2", "n", nDays,nCreatures,nFood) => SimulationParameters(View(printCLI)(getFrame(true)), nDays, nCreatures, nFood)
  }

  def makeSimulation(param: SimulationParameters): IO[Simulator] = for {
    environment <- IO {Environment(BOUNDARIES, makeBoundedFoodCollection(param.nFood), makeOnBoundsCreaturesCollection(param.nCreatures))}
    sim <- IO{DaySimulator(FIRST_DAY, param.nFood, param.nDays, environment, param.view)}
  } yield sim

  // View
  def printCLI(output: Output): IO[Unit] = putStrLn(Output.LastDayParser(output))

  def printFile(output: Output): IO[Unit] = for {
    file <- IO(new File("hello.json"))
    w <- IO(new FileWriter(file))
    _ <- w.write(Output.JsonParser(output))
    _ <- w.close()
  } yield ()

  def update(sView: SimulationView, environment: Environment): IO[Unit] = sView match {
    case view: View => view.update(updateJFrame(environment, view.frame))
    case _ => {}
  }

  val updateJFrame: (Environment, Option[JFrame]) => () => Unit = (environment, jFrame) => () => {

    def _update(frame: JFrame): IO[Unit] = for {
      _ <- Thread.sleep(UPDATE_TIME_MS)
      _ <- frame.getContentPane.removeAll()
      //      visualizer <- Visualizer(environment)
      _ <- frame.getContentPane.add(Visualizer(environment))
      _ <- frame.revalidate()
    } yield ()

    jFrame match {
      case Some(frame) => _update(frame).unsafeRunSync()
      case _ =>
    }
  }

}
