package guiceakka.actors

import akka.actor.Actor
import akka.actor.Actor.Receive
import com.google.inject.Inject

object SampleActorProtocol {

  val name = "sampleActor"

  case class TestMessage(message : String)

  case class TestResponseMessage(message : String)

}

/**
  * Created by avalcepina on 16/10/2016.
  */
class SampleActor @Inject()() extends Actor {
  override def receive: Receive = {
    case _ => println("ok")
  }
}
