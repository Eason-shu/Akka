import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/1 11:43
 * @version: 1.0
 */
class SClient(remoteAddress: String) {
  private implicit val timeout = Timeout(2 seconds)
  private implicit val system = ActorSystem("LocalSystem")
  private val remoteDb = system.actorSelection(
    s"akka.tcp://akkademy@$remoteAddress/user/akkademy-db")
  def set(key: String, value: Object) = {
    remoteDb ? SetRequest(key, value)
  }
  def get(key: String) = {
    remoteDb ? GetRequest(key)
  }
}



case class SetRequest(key: String, value: Object)
case class GetRequest(key: String)
case class KeyNotFoundException(key: String) extends Exception