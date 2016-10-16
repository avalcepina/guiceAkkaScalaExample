package guiceakka.config

import com.google.inject.{AbstractModule, Provider}
import com.typesafe.config.{Config, ConfigFactory}
import guiceakka.config.ConfigModule.ConfigProvider
import net.codingwell.scalaguice.ScalaModule

object ConfigModule {

  class ConfigProvider extends Provider[Config] {
    override def get() = ConfigFactory.load()
  }

}

/**
  * Binds the application configuration to the [[Config]] interface.
  *
  * The nl.weeronline.config is bound as an eager singleton so that errors in the nl.weeronline.config are detected
  * as early as possible.
  */
class ConfigModule extends AbstractModule with ScalaModule {

  override def configure() {
    bind[Config].toProvider[ConfigProvider].asEagerSingleton()
  }

}
