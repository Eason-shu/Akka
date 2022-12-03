import akka.actor.{Actor, Status}
import akka.event.{Logging, LoggingAdapter}

import scala.collection.convert.ImplicitConversions.`map AsJavaMap`
import scala.collection.mutable

/**
 * @description:
 * @author: shu
 * @createDate: 2022/11/28 12:41
 * @version: 1.0
 */
class ScalaRequest extends Actor {
  protected val log: LoggingAdapter = Logging.getLogger(context.system, this)
   val map: mutable.Map[String, Object] = new mutable.HashMap[String, Object]

  override def receive = {

    case SetRequest(key, value) =>
      log.info("received SetRequest - key: {} value: {}", key, value)
      map.put(key, value)
      sender() ! Status.Success

    case GetRequest(key) =>
      log.info("received GetRequest - key: {}", key)
      val response: Option[Object] = map.get(key)

      response match{
        case Some(x) => sender() ! x
        case None => sender() ! Status.Failure(new KeyNotFoundException(key))
      }

    case o => Status.Failure(new ClassNotFoundException)
  }

}


case class SetRequest(key: String, value: Object)
case class GetRequest(key: String)
case class KeyNotFoundException(key: String) extends Exception