import scala.sys.process._

name := "psy4j"
description := "PSY4J Dev Repo"
version := "1.3.0"
fork in run := true
javacOptions := Seq("-target", "11")

scalaVersion := "2.13.1"

libraryDependencies ++= {
  lazy val logbackVersion = "1.2.3"
  lazy val javaFXVersion = "15.0.1"
  lazy val osName = System.getProperty("os.name") match {
    case n if n.startsWith("Linux")   => "linux"
    case n if n.startsWith("Mac")     => "mac"
    case n if n.startsWith("Windows") => "win"
    case _ => throw new Exception("Unknown platform!")
  }
  lazy val javaFXModules = Seq("base", "controls", "graphics")
  Seq(
    "org.scalafx" %% "scalafx" % s"$javaFXVersion-R21",
    "org.slf4j" % "slf4j-api" % "1.7.25",
    "ch.qos.logback" % "logback-core" % logbackVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "com.typesafe.play" %% "play-json" % "2.8.1",
    "commons-io" % "commons-io" % "2.8.0",
    "com.typesafe" % "config" % "1.4.0",
    "com.iheart" %% "ficus" % "1.5.0" % Test,
    "org.scalatest" %% "scalatest" % "3.1.1" % Test
  ) ++ javaFXModules.map( m =>
    "org.openjfx" % s"javafx-$m" % javaFXVersion classifier osName
  )
}

// groupId, SCM, license information
organization := "com.mazhangjing"
homepage := Some(url("https://github.com/corkine/psy4j"))
scmInfo := Some(ScmInfo(url("https://github.com/corkine/psy4j"), "git@github.com:corkine/psy4j.git"))
developers := List(Developer("Corkine Ma", "corkine", "corkine@outlook.com", url("https://mazhangjing.com")))
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))
publishMavenStyle := true

// disable publishw ith scala version, otherwise artifact name will include scala version
// e.g cassper_2.11
crossPaths := false

// add sonatype repository settings
// snapshot versions publish to sonatype snapshot repository
// other versions publish to sonatype staging repository
publishTo := Some(
  if (isSnapshot.value) Opts.resolver.sonatypeSnapshots
  else Opts.resolver.sonatypeStaging
)























/*
mainClass in (Compile, run) := Some("com.mazhangjing.demo.lhl.RunnableApp")
mainClass in assembly := Some("com.mazhangjing.demo.lhl.RunnableApp")
assemblyMergeStrategy in assembly := {
    case manifest if manifest.contains("MANIFEST.MF") =>
      MergeStrategy.discard
    case moduleInfo if moduleInfo.contains("module-info.class") =>
      MergeStrategy.discard
    case referenceOverrides if referenceOverrides.contains("reference-overrides.conf") =>
      MergeStrategy.concat
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
}

lazy val winpkg = taskKey[Unit]("jar 打包 exe")
winpkg := {
  //确保 launchPath 包含 launch4j 安装程序
  //确保 target/scala-2.13 生成的 jar 文件名称和 package.xml 文件内对应，确保 package.xml 中 output 文件夹存在且具有 JRE
  val launchPath = "C:\\Program Files (x86)\\Launch4j"
  val pkg_config = "C:\\Users\\Corkine\\Desktop\\lhlExp\\target\\scala-2.13\\package.xml"
  val output = "C:\\Users\\Corkine\\Desktop\\lhlExperiment_output"
  println(s"cmd /c C: && cd $launchPath && .\\launch4j.jar $pkg_config")
  val ans = s"cmd /c C: && cd $launchPath && .\\launch4j.jar $pkg_config && explorer $output".!!
  println(ans)
}


//Use assembly jar for Universal executable
//For Windows Package, Use launch4j Wrapper, launch4j.jar -> load config -> wrap it.
//For MacOS Package, Use jar2app: jar2app lhlExp-assembly-1.0.jar -n "Psy4J App" -r /Library/Java/JavaVirtualMachines/jdk1.8.0_261.jdk main.app
//Note: The work dir is User's home, data and config should set there.

/*
enablePlugins(WindowsPlugin)
// general package information (can be scoped to Windows)
maintainer := "Corkine Ma <corkine@outlook.com>"
packageSummary := "LHLExperiment"
packageDescription := """LHLExperiment v0.0.1"""

// wix build information
wixProductId := "48c5089b-c8d9-4f7c-ad72-e7ad7963cce2"
wixProductUpgradeId := "673cc705-76a9-47ac-94a7-ce5c1c8f8873"*/

//For Mac Package, Use

/*enablePlugins(JDKPackagerPlugin)
lazy val iconGlob = sys.props("os.name").toLowerCase match {
  case os if os.contains("mac") => "*.icns"
  case os if os.contains("win") => "*.ico"
  case _ => "*.png"
}
jdkPackagerJVMArgs := Seq("-Xmx1g")
maintainer := "CorkineMa"
packageSummary := "LHLExperiment Powered by Psy4J"
packageDescription := "LHLExperiment Powered by Psy4J"
jdkPackagerProperties := Map("app.name" -> name.value, "app.version" -> version.value)
jdkPackagerAppArgs := Seq(maintainer.value, packageSummary.value, packageDescription.value)
jdkPackagerType := "image"
/*(antPackagerTasks in JDKPackager) := (antPackagerTasks in JDKPackager).value orElse {
  for {
    f <- Some(file("/Library/Java/JavaVirtualMachines/jdk1.8.0_261.jdk/Contents/Home/lib/ant-javafx.jar")) if f.exists()
  } yield f
}*/

enablePlugins(UniversalPlugin)*/

*/