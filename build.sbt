organization := "io.malcolmgreaves"

name := "cybozu-language-detection"

version := "1.1.2"

scalaVersion := "2.11.6"

crossScalaVersions := Seq("2.11.6", "2.10.5")

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

val javaVersion = "1.7"

javacOptions in (Compile, compile) ++= Seq("-source", javaVersion, "-target", javaVersion) 

javacOptions in (doc) ++= Seq("-source", javaVersion) 

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

instrumentSettings

packAutoSettings

publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
    else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra := (
  <url>https://github.com/malcolmgreaves/language-detection</url>
  <licenses>
    <license>
      <name>Apache 2.0</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:malcolmgreaves/language-detection.git</url>
    <connection>scm:git:git@github.com:malcolmgreaves/language-detection.git</connection>
  </scm>
  <developers>
    <developer>
      <id>malcolmgreaves</id>
      <name>Malcolm Greaves</name>
      <url>https://github.com/malcolmgreaves</url>
    </developer>
  </developers>
)
