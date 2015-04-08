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
  // Rapture libraries, Json parsing and such
  //"com.propensive" %% "rapture-core" % "1.0.0",
  //"com.propensive" %% "rapture-json-jackson" % "1.0.8",
  //"com.propensive" %% "rapture-uri" % "1.0.0",
  //"com.propensive" %% "rapture-codec" % "1.0.0",
  //"com.propensive" %% "rapture-net" % "0.10.0",
  //
  // Concurrent / Distributed Frameworks 
  //"org.apache.spark" %% "spark-core" % "1.2.0",
  //"com.typesafe.akka" %% "akka-actor" % "2.3.9",
  //
  // ML + NLP
  //"org.apache.spark" %% "spark-mllib" % "1.2.0",
  //"org.scalanlp" %% "breeze-core" % "0.4",
  //"org.scalanlp" %% "breeze-math" % "0.4",
  //"org.scalanlp" % "nak" % "1.1.3",
  //"org.scalanlp" % "chalk" % "1.2.0",
  //"edu.arizona.sista" % "processors" % "3.3",
  //"edu.arizona.sista" % "processors" % "3.3" classifier "models",  
  //
  // Logging and other utilities
  //"org.log4s" %% "log4s" % "1.1.4",
  //"org.apache.logging.log4j" % "log4j" % "2.2",
  //"org.slf4j" % "log4j-over-slf4j" % "1.7.12",
  // Testing
  //"com.typesafe.akka" %% "akka-testkit" % "2.3.9" % "test",
  //"org.scalatest" %% "scalatest" % "2.2.1" % "test"
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

