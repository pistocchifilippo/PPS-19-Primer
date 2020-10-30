# PPS-19-Primer

<a href='https://travis-ci.com/github/pistocchifilippo/PPS-19-Primer/builds'><img src='https://travis-ci.com/pistocchifilippo/PPS-19-Primer.svg?branch=master'></a>

This project is aimed to develop a natural selection simulator.
The project is truely inspired by *Minutelab* project. Link [here](https://labs.minutelabs.io/evolution-simulator/).



## The simulation
The simulator supports a 2D environment containing cratures and food.
The simulation is divided in **days**, where creatures can move into the environment and eat food.
At the end of one day cratures that ate any food *dies*, cratures that ate just one piece of food *survie* and can go through into the simulation, creatures that ate 2 pieces of food can will survieve and *reproduce*.
Simulator supports gene mutations setted just for the speed of creatures.

## The output
The simulation output will be recorded into the file */statistics/statistics_YYYY-MM-DD_hh:mm:ss.mmm.json*.
The only format supported for statistics is JSON.

## How it works
The simulators support two way to operate.
* **Simulation Mode** (SM) where the simulation is aimed to get better performance, having no graphical overhed.
* **Test Mode** (TM) where the simulation will be executed with a graphical panel as long as simulation ca be observed better.

In the initial phase of execution you have to choose the configuration where few parameters will be specified.
* Execution mode.
* Output file format.
* Days of simulation.
* Creature number.
* Food amout.

## Build
The system uses Sbt as build system and dependency manager.
To build:

```
$ git clone https://github.com/pistocchifilippo/PPS-19-Primer.git
$ cd PPS-19-Primer
$ sbt compile
```

To test:

```
$ sbt test
```

To execute:
```
$ sbt run
```


