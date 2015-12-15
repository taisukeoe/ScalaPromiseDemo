import android.Keys._

lazy val root = (project in file(".")).settings(android.Plugin.androidBuild)
  .settings(
    name := "ScalaPromiseSample",
    scalaVersion := "2.11.7",
    versionCode := Some(0),
    versionName := Some("0.1")
  )
