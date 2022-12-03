import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import akka.testkit.TestActorRef;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AkkademyDbTest {

    // 获取Actor系统的引用
    ActorSystem system = ActorSystem.create();

    @Test
    @Ignore
    public void itShouldPlaceKeyValueFromSetMessageIntoMap() {
        // 创建一个actor
        TestActorRef<AkkaDbs> actorRef = TestActorRef.create(system, Props.create(AkkaDbs.class));
        // 发送消息
        actorRef.tell(new SetRequests("key", "value"), ActorRef.noSender());
        // 我们需要检查Actor是否将值存入了map中，确认其行为是否正确
        AkkaDbs akkademyDb = actorRef.underlyingActor();
        assertEquals(akkademyDb.map.get("key"), "value");
    }

    @Test
    public void JavaPongActorTest(){
        // 创建一个actor
        TestActorRef<JavaPongActor> actorRef = TestActorRef.create(system, Props.create(JavaPongActor.class));
        // 发送消息
        actorRef.tell("Ping",ActorRef.noSender());
        System.out.println(actorRef.path());
        // 返回的消息
        actorRef.receive(ReceiveBuilder
                .matchEquals("收到：OVER,OVER消息！", message -> {
                    System.out.printf("收到的key:%s",message);
                })
                .matchAny(o ->  System.out.printf("收到的消息:%s",o))
                .build());
    }

}