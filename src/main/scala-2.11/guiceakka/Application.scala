package guiceakka

import akka.actor.ActorSystem
import com.google.inject.Guice
import guiceakka.actors.{AkkaModule, MainActor}
import guiceakka.config.ConfigModule
import guiceakka.guice.InjectActorSupport

/**
  * Created by avalcepina on 16/10/2016.
  */
class Application extends App with InjectActorSupport {

  val system = ActorSystem("exampleSystem")

  val injector = Guice.createInjector(new ConfigModule, new AkkaModule(system))

  injectTopActor[MainActor](injector)

}
