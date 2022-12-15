package com.shu;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import pojo.Meter;

/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/11 17:25
 * @version: 1.0
 */

public class RouterExample {
    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("RouterExample");

        // 创建路由器
        ActorRef router = system.actorOf(new RoundRobinPool(5).props(Props.create(Meter.class)));

        // 向路由器发送消息
        for (int i = 0; i < 10; i++) {
            router.tell(new Meter(), ActorRef.noSender());
        }
    }
}
