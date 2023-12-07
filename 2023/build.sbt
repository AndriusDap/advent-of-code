name := "advent-of-code"

scalaVersion := "3.3.1"

libraryDependencies ++= Seq(
  "com.softwaremill.quicklens" %% "quicklens" % "1.8.10",
  "com.google.guava" % "guava" % "23.0",
  "org.typelevel" %% "cats-effect" % "3.5.0",
  "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4",
)