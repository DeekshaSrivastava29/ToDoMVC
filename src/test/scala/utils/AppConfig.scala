package utils

import com.typesafe.config.ConfigFactory

object AppConfig {
  // Load the default configuration file (application.conf)
  private val config = ConfigFactory.load()

  // Read the URL property from the configuration file
  val url: String = config.getString("url")
}
