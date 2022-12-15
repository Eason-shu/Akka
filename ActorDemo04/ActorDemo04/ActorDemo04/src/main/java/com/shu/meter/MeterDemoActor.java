package com.shu.meter;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;
import akka.routing.RoundRobinGroup;
import akka.routing.RoundRobinPool;
import pojo.Meter;
import pojo.MeterRequest;

/**
 * @description: 电表Actor
 * @author: shu
 * @createDate: 2022/12/10 16:10
 * @version: 1.0
 */
public class MeterDemoActor extends AbstractActor {

    // 创建路由方式一
    ActorRef workerRouter = context().actorOf(Props.create(MeterDemoActor.class).withRouter(new RoundRobinPool(8)));

    // 创建方式二
    //ActorRef router = context().actorOf(new RoundRobinGroup(actors.map(actor -> actor.path()).props());



    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(MeterRequest.class, x->{
                    System.out.println("收到电表请求消息");
                    sender().tell(new Meter("1001","测试"), self());
                })
                .matchEquals(String.class, System.out::println)
                // 未找到消息
                .matchAny(o ->
                        sender().tell(new Status.Failure(new ClassNotFoundException()), self())
                )
                .build();
    }
}
