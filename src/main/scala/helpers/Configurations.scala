package helpers

import java.io.File

import model.environment
import model.environment.Boundaries
import model.environment.Position.Position

object Configurations {
  def TOP_LEFT: Position = 0.0 -> 0.0

  def BOTTOM_RIGHT: Position = 500.0 -> 500.0

  def BOUNDARIES: Boundaries = environment.Boundaries(TOP_LEFT, BOTTOM_RIGHT)

  def CREATURES_RADIUS = 15

  def CREATURES_ENERGY = 40000

  def CREATURES_SPEED = 3

  def FOOD_RADIUS = 3

  def GOAL_RADIUS = 2

  def FIRST_DAY = 1

  def DELTA_MUTATION = 0.1

  def OUTPUT_PATH = "output.json"

  def VISUALIZER_HEIGHT = 500
  def VISUALIZER_WIDTH = 500
  def SIMULATOR_HEIGHT = 530
  def SIMULATOR_WIDTH = 510
  def SIMULATOR_TITLE = "NATURAL SELECTION SIMULATOR"
  def UPDATE_TIME_MS = 15

  // Gets
  val WELCOME = "                                                                                                                   \n                                                                                                                   \n           .---.            ,--,                                ____                      ___                      \n          /. ./|          ,--.'|                              ,'  , `.                  ,--.'|_                    \n      .--'.  ' ;          |  | :               ,---.       ,-+-,.' _ |                  |  | :,'   ,---.           \n     /__./ \\ : |          :  : '              '   ,'\\   ,-+-. ;   , ||                  :  : ' :  '   ,'\\          \n .--'.  '   \\' .   ,---.  |  ' |      ,---.  /   /   | ,--.'|'   |  || ,---.          .;__,'  /  /   /   |         \n/___/ \\ |    ' '  /     \\ '  | |     /     \\.   ; ,. :|   |  ,', |  |,/     \\         |  |   |  .   ; ,. :         \n;   \\  \\;      : /    /  ||  | :    /    / ''   | |: :|   | /  | |--'/    /  |        :__,'| :  '   | |: :         \n \\   ;  `      |.    ' / |'  : |__ .    ' / '   | .; :|   : |  | ,  .    ' / |          '  : |__'   | .; :         \n  .   \\    .\\  ;'   ;   /||  | '.'|'   ; :__|   :    ||   : |  |/   '   ;   /|          |  | '.'|   :    |         \n   \\   \\   ' \\ |'   |  / |;  :    ;'   | '.'|\\   \\  / |   | |`-'    '   |  / |          ;  :    ;\\   \\  /          \n    :   '  |--\" |   :    ||  ,   / |   :    : `----'  |   ;/        |   :    |          |  ,   /  `----'           \n     \\   \\ ;     \\   \\  /  ---`-'   \\   \\  /          '---'          \\   \\  /            ---`-'                    \n      '---\"       `----'     ,-.----.`----'                           `----'                                       \n                             \\    /  \\                              ____                                           \n                             |   :    \\            ,--,           ,'  , `.                                         \n                             |   |  .\\ :  __  ,-.,--.'|        ,-+-,.' _ |          __  ,-.                        \n                             .   :  |: |,' ,'/ /||  |,      ,-+-. ;   , ||        ,' ,'/ /|                        \n                             |   |   \\ :'  | |' |`--'_     ,--.'|'   |  || ,---.  '  | |' |                        \n                             |   : .   /|  |   ,',' ,'|   |   |  ,', |  |,/     \\ |  |   ,'                        \n                             ;   | |`-' '  :  /  '  | |   |   | /  | |--'/    /  |'  :  /                          \n                             |   | ;    |  | '   |  | :   |   : |  | ,  .    ' / ||  | '                           \n                             :   ' |    ;  : |   '  : |__ |   : |  |/   '   ;   /|;  : |                           \n                             :   : :    |  , ;   |  | '.'||   | |`-'    '   |  / ||  , ;                           \n                             |   | :     ---'    ;  :    ;|   ;/        |   :    | ---'                            \n                             `---'.|             |  ,   / '---'          \\   \\  /                                  \n                 .--.--.       `---`          ____---`-'           ,--,   `----'         ___                       \n                /  /    '.   ,--,           ,'  , `.             ,--.'|                ,--.'|_                     \n               |  :  /`. / ,--.'|        ,-+-,.' _ |        ,--, |  | :                |  | :,'   ,---.    __  ,-. \n               ;  |  |--`  |  |,      ,-+-. ;   , ||      ,'_ /| :  : '                :  : ' :  '   ,'\\ ,' ,'/ /| \n               |  :  ;_    `--'_     ,--.'|'   |  || .--. |  | : |  ' |     ,--.--.  .;__,'  /  /   /   |'  | |' | \n                \\  \\    `. ,' ,'|   |   |  ,', |  |,'_ /| :  . | '  | |    /       \\ |  |   |  .   ; ,. :|  |   ,' \n                 `----.   \\'  | |   |   | /  | |--'|  ' | |  . . |  | :   .--.  .-. |:__,'| :  '   | |: :'  :  /   \n                 __ \\  \\  ||  | :   |   : |  | ,   |  | ' |  | | '  : |__  \\__\\/: . .  '  : |__'   | .; :|  | '    \n                /  /`--'  /'  : |__ |   : |  |/    :  | : ;  ; | |  | '.'| ,\" .--.; |  |  | '.'|   :    |;  : |    \n               '--'.     / |  | '.'||   | |`-'     '  :  `--'   \\;  :    ;/  /  ,.  |  ;  :    ;\\   \\  / |  , ;    \n                 `--'---'  ;  :    ;|   ;/         :  ,      .-./|  ,   /;  :   .'   \\ |  ,   /  `----'   ---'     \n                           |  ,   / '---'           `--`----'     ---`-' |  ,     .-./  ---`-'                     \n                            ---`-'                                        `--`---'                                 \n                                                                                                                   "
  val SM = "1. Simulation mode"
  val TM = "2. Test mode"
  val MODE_REQUEST = "Choose the execution mode " + SM + " " + TM
  val ACCEPT_MODE = List("1","2")

  val OUT_REQUEST = "Output on file? y/n"
  val ACCEPT_OUT = List("y","n")

  val DAYS_REQUEST = "Number of days"
  val CREATURES_REQUEST = "Number of creatures"
  val FOOD_REQUEST = "Number of food"


  val SEPARATOR = File.separator
}
