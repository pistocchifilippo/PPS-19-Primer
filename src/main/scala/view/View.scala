package view

abstract class View {
  def print(): Unit

  //def collectParameters : IO[IOException, Parameters] -> da definire qui con scalaz for-comprehension
}

case class CLIView() extends View {
  override def print(): Unit = ??? // --> print on console
}

case class CLIFileView() extends View {
  override def print(): Unit = ??? // --> print on file
}

trait GUI extends View {
  def update: Unit
}
