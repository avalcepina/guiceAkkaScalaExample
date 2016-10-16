package guiceakka.guice

import akka.actor.{Actor, ActorRef, ActorRefFactory, ActorSystem, Props}
import com.google.inject.Injector

import scala.reflect.ClassTag

/**
  * Created by avalcepina on 12/10/2016.
  *
  * Trait used to manually inject actors
  *
  */
trait InjectActorSupport {

  /**
    * Inject and actor as a child of current actor
    *
    * @param injector
    * @param factory
    * @param tag
    * @tparam A
    * @return
    */
  protected def injectActor[A <: Actor](injector: Injector)(implicit factory: ActorRefFactory, tag: ClassTag[A]): ActorRef =
  factory.actorOf(Props(classOf[ActorProducer[A]], injector, tag.runtimeClass))

  /**
    * Inject and actor as a child of current actor
    *
    * @param name
    * @param injector
    * @param factory
    * @param tag
    * @tparam A
    * @return
    */
  protected def injectActor[A <: Actor](name: String, injector: Injector)(implicit factory: ActorRefFactory, tag: ClassTag[A]): ActorRef =
  factory.actorOf(Props(classOf[ActorProducer[A]], injector, tag.runtimeClass), name)


  /**
    * Inject and actor as a child of current actor
    *
    * @param create
    * @param factory
    * @return
    */
  protected def injectActor(create: => Actor)(implicit factory: ActorRefFactory): ActorRef =
  factory.actorOf(Props(create))

  /**
    * Inject and actor as a child of current actor
    *
    * @param create
    * @param name
    * @param factory
    * @return
    */
  protected def injectActor(create: => Actor, name: String)(implicit factory: ActorRefFactory): ActorRef =
  factory.actorOf(Props(create), name)


  /*
    The following two methods should be used when there's need to manually inject an actor outside other actors.
     Tipically this happens for top level actors only
    The same result could be also achieved by doing the following:

    //This must be implicit
    implicit val system = ActorSystem("someName")

    //Get the injector loading the appropriate modules
    val injector = Guice.createInjector(...)

    val someActorFactory = injector.getInstance(classOf[SomeActorFactory])

    //Inject the actor using the factory and passing some params to it
    injectActor(someActorFactory(...));
  */

  /**
    * Inject and actor as a top level actor (outside of other actors)
    *
    * @param injector
    * @param tag
    * @tparam A
    * @return
    */
  protected def injectTopActor[A <: Actor](injector: Injector)(implicit tag: ClassTag[A]): ActorRef =
  injector.getInstance(classOf[ActorSystem]).actorOf(Props(classOf[ActorProducer[A]], injector, tag.runtimeClass))

  /**
    * Inject and actor as a top level actor (outside of other actors)
    *
    * @param name
    * @param injector
    * @param tag
    * @tparam A
    * @return
    */
  protected def injectTopActor[A <: Actor](name: String, injector: Injector)(implicit tag: ClassTag[A]): ActorRef =
  injector.getInstance(classOf[ActorSystem]).actorOf(Props(classOf[ActorProducer[A]], injector, tag.runtimeClass), name)


}
