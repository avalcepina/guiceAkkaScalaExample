package guiceakka.guice
import akka.actor.{Actor, IndirectActorProducer}
import com.google.inject.Injector

/**
  * Created by avalcepina on 11/10/2016.
  *
  * Actor producer class extending IndirectActor producer
  * Allows for injection of dependencies in actors
  *
  */
class ActorProducer[A <: Actor](injector: Injector, clazz: Class[A]) extends IndirectActorProducer {

  def actorClass = clazz

  def produce() = injector.getBinding(clazz).getProvider.get()

}