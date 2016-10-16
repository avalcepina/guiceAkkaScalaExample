package guiceakka.actors

import akka.actor.{Actor, ActorRef}
import akka.actor.Actor.Receive
import com.google.inject.Inject
import com.google.inject.name.Named
import com.typesafe.config.Config

/**
  * Created by avalcepina on 16/10/2016.
  */
class MainActor @Inject()(config : Config, @Named("sampleActor") sampleActor : ActorRef  ) extends Actor {
  override def receive: Receive = {

    case _ => ???

  }
}
