package com.shu.meter;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;
import pojo.Meter;
import pojo.MeterRequest;

/**
 * @description: 电表Actor
 * @author: shu
 * @createDate: 2022/12/10 16:10
 * @version: 1.0
 */
public class MeterDemoActor extends AbstractActor {
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
