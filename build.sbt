name := "language-detection"

version := "0.1"

organization := "cybozu"

resolvers ++= Seq(
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

// Take note of the "%%" vs "%"! Double means use the Scala major (e.g. 2.11 vs 2.10) version dependency.
libraryDependencies ++= Seq(
  "org.hamcrest" % "hamcrest-core" % "1.3",
  //
  // Testing
  "junit" % "junit" % "4.12",
  "com.novocode" % "junit-interface" % "0.10" % "test" exclude("junit", "junit-dep")
)

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

instrumentSettings

packAutoSettings

