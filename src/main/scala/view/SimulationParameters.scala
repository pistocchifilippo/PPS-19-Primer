package view

/** Parameters to be collected to start the simulation
 * */
case class SimulationParameters(
                     view: SimulationView,
                     nDays: Int,
                     nCreatures: Int,
                     nFood: Int
                     )