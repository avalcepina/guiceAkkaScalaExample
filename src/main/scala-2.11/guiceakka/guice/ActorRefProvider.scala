package guiceakka.guice

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.google.inject.{Inject, Injector, Provider}

import scala.reflect.ClassTag


/**
  * Created by avalcepina on 11/10/2016.
  *
  * Provider for actorRefs
  *
  */
class ActorRefProvider[T <: Actor : ClassTag](name: String, props: Props => Props) extends Provider[ActorRef] {

  @Inject private var actorSystem: ActorSystem = _
  @Inject private var injector: Injector = _

  lazy val get = {

    val creation = Props(classOf[ActorProducer[T]], injector, runtimeClass)
    actorSystem.actorOf(props(creation), name)
  }


  def runtimeClass[Class[T]](implicit ct: ClassTag[T]): Class[T] = ct.runtimeClass.asInstanceOf[Class[T]]

  def instanceOf[T](implicit ct: ClassTag[T]): T = instanceOf(ct.runtimeClass.asInstanceOf[Class[T]])

  def instanceOf[T](clazz: Class[T]): T = clazz.newInstance()

}
