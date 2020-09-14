package controller

import controller.simulator.{GUIDayStepSimulator, Simulator}

trait SimulationController extends Simulator {

  abstract override def next(): Simulator = {
    val newSim = super.next()
    println("gui updated")
    new GUIDayStepSimulator(newSim.environment, newSim.view)
  }

}