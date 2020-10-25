package helpers

import java.io.File

import model.environment
import model.environment.Boundaries
import model.environment.Position.Position

object Configurations {
  // Gets
  val WELCOME = "                                                                                                                   \n                                                                                                                   \n           .---.            ,--,                                ____                      ___                      \n          /. ./|          ,--.'|                              ,'  , `.                  ,--.'|_                    \n      .--'.  ' ;          |  | :               ,---.       ,-+-,.' _ |                  |  | :,'   ,---.           \n     /__./ \\ : |          :  : '              '   ,'\\   ,-+-. ;   , ||                  :  : ' :  '   ,'\\          \n .--'.  '   \\' .   ,---.  |  ' |      ,---.  /   /   | ,--.'|'   |  || ,---.          .;__,'  /  /   /   |         \n/___/ \\ |    ' '  /     \\ '  | |     /     \\.   ; ,. :|   |  ,', |  |,/     \\         |  |   |  .   ; ,. :         \n;   \\  \\;      : /    /  ||  | :    /    / ''   | |: :|   | /  | |--'/    /  |        :__,'| :  '   | |: :         \n \\   ;  `      |.    ' / |'  : |__ .    ' / '   | .; :|   : |  | ,  .    ' / |          '  : |__'   | .; :         \n  .   \\    .\\  ;'   ;   /||  | '.'|'   ; :__|   :    ||   : |  |/   '   ;   /|          |  | '.'|   :    |         \n   \\   \\   ' \\ |'   |  / |;  :    ;'   | '.'|\\   \\  / |   | |`-'    '   |  / |          ;  :    ;\\   \\  /          \n    :   '  |--\" |   :    ||  ,   / |   :    : `----'  |   ;/        |   :    |          |  ,   /  `----'           \n     \\   \\ ;     \\   \\  /  ---`-'   \\   \\  /          '---'          \\   \\  /            ---`-'                    \n      '---\"       `----'     ,-.----.`----'                           `----'                                       \n                             \\    /  \\                              ____                                           \n                             |   :    \\            ,--,           ,'  , `.                                         \n                             |   |  .\\ :  __  ,-.,--.'|        ,-+-,.' _ |          __  ,-.                        \n                             .   :  |: |,' ,'/ /||  |,      ,-+-. ;   , ||        ,' ,'/ /|                        \n                             |   |   \\ :'  | |' |`--'_     ,--.'|'   |  || ,---.  '  | |' |                        \n                             |   : .   /|  |   ,',' ,'|   |   |  ,', |  |,/     \\ |  |   ,'                        \n                             ;   | |`-' '  :  /  '  | |   |   | /  | |--'/    /  |'  :  /                          \n                             |   | ;    |  | '   |  | :   |   : |  | ,  .    ' / ||  | '                           \n                             :   ' |    ;  : |   '  : |__ |   : |  |/   '   ;   /|;  : |                           \n                             :   : :    |  , ;   |  | '.'||   | |`-'    '   |  / ||  , ;                           \n                             |   | :     ---'    ;  :    ;|   ;/        |   :    | ---'                            \n                             `---'.|             |  ,   / '---'          \\   \\  /                                  \n                 .--.--.       `---`          ____---`-'           ,--,   `----'         ___                       \n                /  /    '.   ,--,           ,'  , `.             ,--.'|                ,--.'|_                     \n               |  :  /`. / ,--.'|        ,-+-,.' _ |        ,--, |  | :                |  | :,'   ,---.    __  ,-. \n               ;  |  |--`  |  |,      ,-+-. ;   , ||      ,'_ /| :  : '                :  : ' :  '   ,'\\ ,' ,'/ /| \n               |  :  ;_    `--'_     ,--.'|'   |  || .--. |  | : |  ' |     ,--.--.  .;__,'  /  /   /   |'  | |' | \n                \\  \\    `. ,' ,'|   |   |  ,', |  |,'_ /| :  . | '  | |    /       \\ |  |   |  .   ; ,. :|  |   ,' \n                 `----.   \\'  | |   |   | /  | |--'|  ' | |  . . |  | :   .--.  .-. |:__,'| :  '   | |: :'  :  /   \n                 __ \\  \\  ||  | :   |   : |  | ,   |  | ' |  | | '  : |__  \\__\\/: . .  '  : |__'   | .; :|  | '    \n                /  /`--'  /'  : |__ |   : |  |/    :  | : ;  ; | |  | '.'| ,\" .--.; |  |  | '.'|   :    |;  : |    \n               '--'.     / |  | '.'||   | |`-'     '  :  `--'   \\;  :    ;/  /  ,.  |  ;  :    ;\\   \\  / |  , ;    \n                 `--'---'  ;  :    ;|   ;/         :  ,      .-./|  ,   /;  :   .'   \\ |  ,   /  `----'   ---'     \n                           |  ,   / '---'           `--`----'     ---`-' |  ,     .-./  ---`-'                     \n                            ---`-'                                        `--`---'                                 \n                                                                                                                   "

  val SM = "1. Simulation mode"

  val TM = "2. Test mode"

  val MODE_REQUEST: String = "Choose the execution mode " + SM + " " + TM

  val ACCEPT_MODE = List("1", "2")

  val OUT_REQUEST = "Output on file? y/n"

  val ACCEPT_OUT = List("y", "n")

  val DAYS_REQUEST = "Number of days"

  val CREATURES_REQUEST = "Number of creatures"

  val FOOD_REQUEST = "Number of food"

  val SEPARATOR: String = File.separator

  val TOP_LEFT: Position = 0.0 -> 0.0

  val BOTTOM_RIGHT: Position = 500.0 -> 500.0

  val BOUNDARIES: Boundaries = environment.Boundaries(TOP_LEFT, BOTTOM_RIGHT)

  val CREATURES_RADIUS = 15

  val CREATURES_ENERGY = 80000

  val CREATURES_SPEED = 3

  val FOOD_RADIUS = 3

  val GOAL_RADIUS = 2

  val FIRST_DAY = 1

  val DELTA_MUTATION = 0.1

  val OUTPUT_PATH = "output.json"

  val VISUALIZER_HEIGHT = 500

  val VISUALIZER_WIDTH = 500

  val SIMULATOR_HEIGHT = 530

  val SIMULATOR_WIDTH = 510

  val SIMULATOR_TITLE = "NATURAL SELECTION SIMULATOR"

  val UPDATE_TIME_MS = 15
}
