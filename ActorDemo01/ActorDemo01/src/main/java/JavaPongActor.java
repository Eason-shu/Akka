import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;

/**
 * @description: Java 收消息测试
 * @author: shu
 * @createDate: 2022/11/27 16:10
 * @version: 1.0
 */
public class JavaPongActor extends AbstractActor {

    public PartialFunction receive() {
        // Actor字符匹配
        return ReceiveBuilder
                .matchEquals("Ping", s ->
//                        System.out.println("收到消息："+s.toString()))
                        sender().tell("Pong", ActorRef.noSender()))
                .matchAny(x ->
                        sender().tell(
                                new Status.Failure(new Exception("unknown message")), self()
                        ))
                .build();
    }
}