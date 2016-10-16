name := "guice_akka_scala_example"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(

  // akka actors
  "com.typesafe.akka" %% "akka-actor" % "2.4.11",
  "com.typesafe.akka" %% "akka-testkit" % "2.4.11",

  // scalatest for testing
  // http://www.scalatest.org
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",

  // guice for scala
  //https://github.com/codingwell/scala-guice
  "net.codingwell" %% "scala-guice" % "4.1.0",

  //guice assisted injection
  //https://github.com/google/guice/wiki/AssistedInject
  "com.google.inject.extensions" % "guice-assistedinject" % "4.1.0"


)