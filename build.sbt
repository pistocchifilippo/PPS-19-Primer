
name := "PPS-19-Primer"

version := "0.1"

scalaVersion := "2.12.10"

// Io effect scalaz
libraryDependencies += "org.scalaz" %% "scalaz-ioeffect" % "2.9.0"

// Scalatest
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.0" % "test"

// Org Json
//libraryDependencies += "org.json" % "json" % "20200518"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.4"

// cats effect
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.2.0"
