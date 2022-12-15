import akka.actor.{ActorIdentity, ActorRef, ActorSelection, ActorSystem, Identify, Props, UntypedActor}

/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/15 11:28
 * @version: 1.0
 */
class
LookupScalaActor extends UntypedActor  {

   var target: ActorRef = getContext.actorOf(Props.create(classOf[TargetActor]), "childActor")



  override def onReceive(msg: Any): Unit = {
    if (msg.isInstanceOf[String]) if ("find" == msg) {
      val as = getContext.actorSelection("targetActor")
      as.tell(new Identify("A001"), getSelf)
    }
    else { // ActorIdentity 系统默认消息
      msg match {
        case ai: ActorIdentity =>
          if (ai.correlationId == "A001") {
            val ref = ai.getRef
            if (ref != null) {
              System.out.println("ActorIdentity: " + ai.correlationId + "" + ref)
              ref.tell("hello target", getSelf)
            }
          }
        case _ => unhandled(msg)
      }
    }
  }

  def main(args: Array[String]): Unit = { //参数为ActorSystem的名字，可以不传
    val system = ActorSystem.create("sys")
    //参数分别是构造器和Actor的名字，名字可以不传
    val actorRef = system.actorOf(Props.create(classOf[LookupActor]), "actorDemo")
    // 发送消息
    actorRef.tell("find", ActorRef.noSender)
  }
}

