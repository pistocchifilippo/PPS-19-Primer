package view.utils

import view.graphic.SimulationView

/** Parameters to be collected to start the simulation
 * */
case class SimulationParameters(
                     view: SimulationView,
                     nDays: Int,
                     nCreatures: Int,
                     nFood: Int
                     )
