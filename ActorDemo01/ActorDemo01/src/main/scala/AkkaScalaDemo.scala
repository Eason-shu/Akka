import akka.actor.{ActorRef, ActorSystem, Props, UntypedActor}
import akka.event.{Logging, LoggingAdapter}

/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/15 10:48
 * @version: 1.0
 */
class AkkaScalaDemo extends UntypedActor{
  // 日志
  val log: LoggingAdapter =Logging(context.system,this);
  // ActorSystem
  val system: ActorSystem = ActorSystem.create("sysScala");
  /**
   * 该Actor下的子Actor，受到当前Actor的监督
   */
  val childActor: ActorRef = getContext.actorOf(Props.create(classOf[JavaPongActor]), "childActor")
  /**
   * 重写方法
   * @param message
   */
  override def onReceive(message: Any): Unit = {
    if(message.isInstanceOf[String]){
      log.info(message.toString)
    }
    else {
      unhandled();
    }
  }

  /** 工厂模式Props
   *
   * @param response
   * @return
   */
  def props(response: String): Props = Props.create(classOf[AkkaDemo], response)

  /**
   * 主函数
   * @param args
   */
  def main(args: Array[String]): Unit = {
    val actorRef = system.actorOf(Props.create(classOf[AkkaScalaDemo]), "actorScalaDemo")
  }
}
