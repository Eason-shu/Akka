
import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import pojo.GetRequest;
import pojo.KeyNotFoundException;
import pojo.SetRequest;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: shu
 * @createDate: 2022/11/28 12:31
 * @version: 1.0
 */
public class RequestActor extends AbstractActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);


    protected final Map<String, Object> map = new HashMap<>();

    @Override
    public void preStart() throws Exception {
        log.info("ToFindRemoteActor is starting");
    }

    @Override
    public Receive createReceive() {
        return  ReceiveBuilder.create()
                // 设置消息
                .match(SetRequest.class, message -> {
                    // 打印消息
                    log.info("Received Set request: {}", message.key);
                    // 缓存消息
                    map.put(message.key, message.value);
                    // 回应消息
                    sender().tell(new Status.Success(message.key), self());
                })
                // 得到消息
                .match(GetRequest.class, message -> {
                    // 打印日志
                    log.info("Received Get request: {}", message.key);
                    // 获取消息
                    Object value = (Object) map.get(message.key);
                    Object response = (value!= null)
                            ? value
                            : new Status.Failure(new KeyNotFoundException(message.key));
                    // 响应消息
                    sender().tell(response, self());
                })
                // 未找到消息
                .matchAny(o ->
                        sender().tell(new Status.Failure(new ClassNotFoundException()), self())
                )
                .build();

    }


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {

//        Config config = ConfigFactory.parseString(
//                        "akka.remote.netty.tcp.port=" + 2551)
//                .withFallback(ConfigFactory.load("application.conf"));

        // Create an Akka system
        ActorSystem system = ActorSystem.create("akkademy");



        // Create an actor
        ActorRef ref = system.actorOf(Props.create(RequestActor.class), "akkademy-db");
        

        System.out.println(ref);

    }

}



