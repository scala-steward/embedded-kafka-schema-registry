import sbt._

object Dependencies {

  object CustomResolvers {
    lazy val Confluent = "confluent" at "https://packages.confluent.io/maven/"
    // Since v5.5.0
    lazy val Jitpack = "jitpack" at "https://jitpack.io"

    lazy val resolvers: Seq[Resolver] = Seq(Confluent, Jitpack)
  }

  object Versions {
    val Scala3            = "3.3.6"
    val Scala213          = "2.13.16"
    val Scala212          = "2.12.20"
    val EmbeddedKafka     = "3.9.1"
    val ConfluentPlatform = "7.9.2"
    val Slf4j             = "1.7.36"
    val ScalaTest         = "3.2.19"
  }

  object Common {
    // Exclude any transitive Kafka dependency to prevent runtime errors.
    // They tend to evict Apache's since their version is greater
    lazy val confluentDeps: Seq[ModuleID] = Seq(
      "io.confluent" % "kafka-avro-serializer" % Versions.ConfluentPlatform % Test,
      "io.confluent" % "kafka-schema-registry" % Versions.ConfluentPlatform
    ).map(_ excludeAll ExclusionRule().withOrganization("org.apache.kafka"))

    lazy val testDeps: Seq[ModuleID] = Seq(
      "org.slf4j"      % "slf4j-reload4j"           % Versions.Slf4j,
      "org.scalatest" %% "scalatest-wordspec"       % Versions.ScalaTest,
      "org.scalatest" %% "scalatest-shouldmatchers" % Versions.ScalaTest
    ).map(_ % Test)
  }

  object EmbeddedKafkaSchemaRegistry {
    lazy val prodDeps: Seq[ModuleID] = Seq(
      "io.github.embeddedkafka" %% "embedded-kafka" % Versions.EmbeddedKafka
    )

  }

  object KafkaStreams {
    lazy val prodDeps: Seq[ModuleID] = Seq(
      "io.github.embeddedkafka" %% "embedded-kafka-streams" % Versions.EmbeddedKafka
    )
  }

}
