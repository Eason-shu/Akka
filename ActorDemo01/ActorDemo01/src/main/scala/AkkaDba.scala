import akka.actor.{AbstractActor, Actor}
import akka.event.Logging
import java.util


/**
 * @description: Scala-AkkaDba
 * @author: shu
 * @createDate: 2022/10/27 20:52
 * @version: 1.0
 */
class AkkaDba extends Actor {
  val map =new util.HashMap[String,Object]
  val log=Logging(context.system,this);

  // 接受消息
  override def receive: Receive = {

    // 指定的消息
    case SetRequest(key,value)=>{
      log.info("收到的key:{},value:{}",key,value)
      map.put(key,value);
    }

    // 默认消息
    case 0 =>{
      log.info("收到一个错误的消息")
    }

  }

}
