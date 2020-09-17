package controller.simulator

import model.Environment

trait Simulator extends Iterator [Simulator] {
  def environment: Environment
  def executedStep: Int
}