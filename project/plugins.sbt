logLevel := Level.Debug
addSbtPlugin("com.eed3si9n"  %  "sbt-assembly"  %  "0.14.5")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.7.6")

// sbt-sonatype plugin used to publish artifact to maven central via sonatype nexus
// sbt-pgp plugin used to sign the artifcat with pgp keys
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.3")
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.1")
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.13")