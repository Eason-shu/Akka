import akka.actor.{Actor, Status}
import sun.rmi.runtime.Log

/**
 * @description: Scala 收消息测试
 * @author: shu
 * @createDate: 2022/11/27 17:00
 * @version: 1.0
 */
class ScalaPongActor extends Actor {
  override def receive: Receive = {
    case "Ping" => sender() ! "Pong"
    case "failed" => sender() ! "failed"
    case _ =>
      sender() ! Status.Failure(new Exception("unknown message"))
  }
}