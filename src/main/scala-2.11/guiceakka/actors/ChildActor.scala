package guiceakka.actors

import akka.actor.Actor
import com.google.inject.Inject
import com.typesafe.config.Config
import guiceakka.actors.ChildActorProtocol.{TestMessage, TestResponseMessage}

object ChildActorProtocol {

  val name = "childActor"

  case class TestMessage(message : String)

  case class TestResponseMessage(message : String)

}

/**
  * Created by avalcepina on 16/10/2016.
  */
class ChildActor @Inject()(config : Config) extends Actor {
  override def receive: Receive = {
    case TestMessage =>
      sender ! TestResponseMessage("Got your message")

    case _  => ???
  }
}
