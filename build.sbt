name := "language-detection"

version := "0.1"

organization := "cybozu"

scalaVersion := "2.11.6"

val buildSettings = Defaults.defaultSettings ++ Seq(
  javaOptions += "-Xmx4G",
  javaOptions += "-XX:MaxPermSize=1024"
)

resolvers ++= Seq(
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Akka Repository" at "http://repo.akka.io/releases/",
  "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Twitter Repository" at "http://maven.twttr.com/"
)

// Take note of the "%%" vs "%"! Double means use the Scala major (e.g. 2.11 vs 2.10) version dependency.
libraryDependencies ++= Seq(
  "org.hamcrest" % "hamcrest-core" % "1.3",
  //
  // Testing
  "junit" % "junit" % "4.12",
  "com.novocode" % "junit-interface" % "0.10" % "test" exclude("junit", "junit-dep")
)

scalacOptions ++= Seq(
  "-optimize",
  "-target:jvm-1.8",
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions", 
  "-language:experimental.macros",
  "-unchecked",
  "-Xfatal-warnings", 
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture",
  "-Yinline-warnings"
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-v")

testOptions in Test += Tests.Argument("-oF")

instrumentSettings

packAutoSettings

fork := false

