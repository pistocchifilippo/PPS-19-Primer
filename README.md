# PPS-19-Primer

<a href='https://travis-ci.com/github/pistocchifilippo/PPS-19-Primer/builds'><img src='https://travis-ci.com/pistocchifilippo/PPS-19-Primer.svg?branch=master'></a>

This project is aimed to develop a natural selection simulator.

The project is truely inspired by *Minutelab* project. Link [here](https://labs.minutelabs.io/evolution-simulator/).


## The simulation
The simulator supports a 2D environment containing Blos divided in cratures and food.

The simulation is performed in **days**: at the beginning of each day the creatures start moving from the boundaries into the environment with the purpose of eating food until they run out of energy.
At the end of each day, the cratures that have not eaten food *die*, the creatures that have eaten just one piece of food *survive* to the next day and creatures that ate 2 pieces of food will survive and *reproduce*.

As the simulation advance, creatures that survived through the days will undergo a gene mutation, setted just for their speed.


## The output
The simulation output will be recorded into the file */statistics/statistics_YYYY-MM-DD_hh:mm:ss.mmm.json*.

The only supported format for statistics is JSON.


## How it works
The simulators support two way to operate.
* **Simulation Mode** (SM) where the simulation is aimed to provide a better performance, having no graphical overhed.
* **Test Mode** (TM) where the simulation will be executed with a graphical panel as long as simulation can be observed day by day.

In the initial phase of execution you have to choose the configuration where few parameters will be specified.
* The execution mode (Simulation Mode or Test Mode).
* The output format (file or console).
* The amount of days of simulation.
* The number of starting creatures.
* The food amount for each day.


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


## Authors
Developed for academic purpose by Filippo Pistocchi (filippo.pistocchi4@studio.unibo.it) and Leonardo Perugini (leonardo.perugini2@studio.unibo.it). 

