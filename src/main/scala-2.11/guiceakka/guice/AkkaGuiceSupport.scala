package guiceakka.guice

import akka.actor.{Actor, ActorRef, Props}
import com.google.inject._
import com.google.inject.assistedinject.FactoryModuleBuilder
import com.google.inject.name.Names
import com.google.inject.util.Providers
import net.codingwell.scalaguice.ScalaModule

import scala.reflect.ClassTag

/**
  * Created by avalcepina on 10/10/2016.
  *
  * Trait extending ScalaModule and providing integration between Akka and Guice
  *
  */
trait AkkaGuiceSupport extends ScalaModule {

  /**
    * Binds an actor eagerly
    *
    * @param name  the actor name
    * @param props the props
    * @tparam T the actor class
    */
  def bindActor[T <: Actor : ClassTag](name: String, props: Props => Props = identity): Unit = {
    binderAccess.bind(classOf[ActorRef])
      .annotatedWith(Names.named(name))
      .toProvider(Providers.guicify(providerOf[T](name, props)))
      .asEagerSingleton()
  }

  /**
    * Binds and actor lazily
    *
    * @param name  the actor name
    * @param props the props
    * @tparam T the actor class
    */
  def bindActorLazily[T <: Actor : ClassTag](name: String, props: Props => Props = identity): Unit = {
    binderAccess.bind(classOf[ActorRef])
      .annotatedWith(Names.named(name))
      .toProvider(Providers.guicify(providerOf[T](name, props)))
  }

  private def providerOf[T <: Actor : ClassTag](name: String, props: Props => Props = identity): Provider[ActorRef] =
    new ActorRefProvider(name, props)

  /** Bind an actor factory.
    *
    * This is useful for when you want to have child actors injected, and want to pass parameters into them, as well as
    * have Guice provide some of the parameters.  It is intended to be used with Guice's AssistedInject feature.
    *
    * Let's say you have an actor that looks like this:
    *
    * {{{
    * class MyChildActor @Inject() (db: Database, @Assisted id: String) extends Actor {
    *  ...
    * }
    * }}}
    *
    * So `db` should be injected, while `id` should be passed.  Now, define a trait that takes the id, and returns
    * the actor:
    *
    * {{{
    * trait MyChildActorFactory {
    *  def apply(id: String): Actor
    * }
    * }}}
    *
    * Now you can use this method to bind the child actor in your module:
    *
    * {{{
    *  class MyModule extends AbstractModule with AkkaGuiceSupport {
    *    def configure = {
    *      bindActorFactory[MyChildActor, MyChildActorFactory]
    *    }
    *  }
    * }}}
    *
    * Now, when you want an actor to instantiate this as a child actor, inject `MyChildActorFactory`:
    *
    * {{{
    * class MyActor @Inject() (myChildActorFactory: MyChildActorFactory) extends Actor with ActorInject {
    *
    *  def receive {
    *    case CreateChildActor(id) =>
    *      val child: ActorRef = injectActor(myChildActoryFactory(id))
    *      sender() ! child
    *  }
    * }
    * }}}
    *
    * @tparam ActorClass   The class that implements the actor that the factory creates
    * @tparam FactoryClass The class of the actor factory
    */
  def bindActorFactory[ActorClass <: Actor : ClassTag, FactoryClass: ClassTag]: Unit = {
    binderAccess.install(new FactoryModuleBuilder()
      .implement(classOf[Actor], implicitly[ClassTag[ActorClass]].runtimeClass.asInstanceOf[Class[_ <: Actor]])
      .build(implicitly[ClassTag[FactoryClass]].runtimeClass))
  }


}
