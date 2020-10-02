package view.utils

import view.graphic.BaseView

/** Parameters to be collected to start the simulation
 * */
case class SimulationParameters(
                                 view: BaseView,
                                 nDays: Int,
                                 nCreatures: Int,
                                 nFood: Int
                     )
