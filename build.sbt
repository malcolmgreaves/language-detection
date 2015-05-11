name := "language-detection"

version := "1.0.1"

scalaVersion := "2.11.6"

crossScalaVersions := Seq("2.11.6", "2.10.5")

organization := "cybozu"

resolvers ++= Seq(
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

// Take note of the "%%" vs "%"! Double means use the Scala major (e.g. 2.11 vs 2.10) version dependency.
libraryDependencies ++= Seq(
  "org.hamcrest" % "hamcrest-core" % "1.3",
  "net.arnx" % "jsonic" % "1.3.7",
  //
  // Testing
  "junit" % "junit" % "4.12" % "test",
  "com.novocode" % "junit-interface" % "0.10" % "test" exclude("junit", "junit-dep")
)

mappings in (Compile, packageBin) <+= baseDirectory map { base =>
 (base / "src" / "main" / "java" / "com" / "cybozu" / "labs" / "langdetect" / "util" / "messages.properties" ) -> "com/cybozu/labs/langdetect/util/messages.properties"
}

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

instrumentSettings

packAutoSettings

