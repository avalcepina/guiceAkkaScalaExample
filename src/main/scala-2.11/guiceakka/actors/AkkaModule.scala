package guiceakka.actors

/**
  * Created by avalcepina on 07/10/2016.
  */

import akka.actor.ActorSystem
import guiceakka.guice.AkkaGuiceSupport

class AkkaModule(system: ActorSystem) extends AkkaGuiceSupport {
  def configure(): Unit = {
    bind(classOf[ActorSystem]).toInstance(system)

    bindActor[SampleActor]("sampleActor")

  }
}