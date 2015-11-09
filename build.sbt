// use sbt-dev-settings to configure
import com.nitro.build._
import PublishHelpers._

// GAV coordinates
//
lazy val pName = "language-detection"
lazy val semver = SemanticVersion(0, 1, 0, isSnapshot = false)
organization := "io.malcolmgreaves"
name := pName
version := semver.toString

// scala & java, compilation & runtime
//
scalaVersion := "2.11.7"
CompileScalaJava.librarySettings(CompileScalaJava.Config.spark)
lazy val javaVersion = "1.7"
javacOptions in (Compile, compile) ++= Seq("-source", javaVersion, "-target", javaVersion)
javacOptions in (doc) ++= Seq("-source", javaVersion)

// dependencies and their resolvers
//
libraryDependencies ++= Seq(
  "org.hamcrest" % "hamcrest-core" % "1.3",
  "net.arnx"     % "jsonic"        % "1.3.7",
  // testing
  "io.malcolmgreaves"    %% "sparkmod"       % "0.1.1-SNAPSHOT" % Test,
  "com.gonitro.research" %% "spark-testing"  % "0.0.1" % Test,
  "org.scalatest"        %% "scalatest"      % "2.2.4" % Test,
  "junit"                % "junit"           % "4.12"  % Test,
  "com.novocode"         % "junit-interface" % "0.10"  % Test exclude("junit", "junit-dep")
)
resolvers := Seq(
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)

// publishing settings
//
Publish.settings(
  repo = Repository.github("malcolmgreaves", pName),
  developers =
    Seq(
      Developer("mgreaves", "Malcolm Greaves", "greaves.malcolm@gmail.com", new URL("https", "github.com", "/malcolmgreaves"))
    ),
  art = ArtifactInfo.sonatype(semver),
  lic = License.apache20
)

// unit test configuration
//
fork in Test              := false
parallelExecution in Test := true
testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")
