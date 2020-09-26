package view.utils

import java.awt.Dimension

import helpers.Configurations.{SIMULATOR_HEIGHT, SIMULATOR_TITLE, SIMULATOR_WIDTH}
import javax.swing.JFrame

/** Utilities module for [[view.SimulationView]] elements
 * */
object ViewUtils {

  /** Creates a new [[JFrame]] element
   *
   * @return a new `visible` [[JFrame]] with fixed `size` and `title`, as defined in [[helpers.Configurations]]
   * */
  def buildFrame(): JFrame = new JFrame(SIMULATOR_TITLE){
    setDefaultCloseOperation(3)
    setSize(new Dimension(SIMULATOR_WIDTH, SIMULATOR_HEIGHT))
    setLocationRelativeTo(null)
    setVisible(true)
  }
}
