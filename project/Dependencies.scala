import sbt._

object Dependencies {
  val compilerOptions = Seq(
    "-target:jvm-1.8",
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-unchecked",
    "-feature",
    "-Xfatal-warnings",
    "-Ywarn-dead-code",
    "-Ywarn-numeric-widen",
    "-Ywarn-value-discard",
    "-Xfuture",
    "-Ypartial-unification"
  )

  lazy val deps = Seq(
    "io.monix"      %% "monix"     % "3.0.0-RC1",
    "org.scalatest" %% "scalatest" % "3.0.5",
    "org.tpolecat"  %% "atto-core" % "0.6.3",
    "co.fs2"        %% "fs2-core"  % "0.10.4",
    "co.fs2"        %% "fs2-io"    % "0.10.4"
  )
}
