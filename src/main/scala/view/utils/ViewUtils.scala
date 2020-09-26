package view.utils

import java.awt.Dimension

import helpers.Configurations.{SIMULATOR_HEIGHT, SIMULATOR_TITLE, SIMULATOR_WIDTH}
import javax.swing.JFrame

/** Utilities module for [[view.SimulationView]] elements
 * */
object ViewUtils {


  def checkParameters(mode: String, file: String, nDays: Int, nCreatures: Int, nFood: Int): Boolean ={
    ((mode equals "1" )|| (mode equals "2")) &&
      ((file equals "y") || (file equals "n")) &&
      (nDays >= 0) && (nCreatures > 0) && (nFood > 0)
  }


  def getFrame(bool: Boolean): Option[JFrame] = bool match {
    case _ if bool => Option(buildFrame())
    case _ => Option.empty
  }

  /** Creates a new [[JFrame]] element
   *
   * @return a new `visible` [[JFrame]] with fixed `size` and `title`, as defined in [[helpers.Configurations]]
   * */
  private def buildFrame(): JFrame = new JFrame(SIMULATOR_TITLE){
    setDefaultCloseOperation(3)
    setSize(new Dimension(SIMULATOR_WIDTH, SIMULATOR_HEIGHT))
    setLocationRelativeTo(null)
    setVisible(true)
  }
}
