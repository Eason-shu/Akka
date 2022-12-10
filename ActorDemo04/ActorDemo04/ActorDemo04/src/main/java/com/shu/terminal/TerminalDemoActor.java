package com.shu.terminal;

import akka.actor.*;
import akka.io.Tcp;
import akka.japi.Function;
import akka.japi.pf.DeciderBuilder;
import akka.japi.pf.ReceiveBuilder;
import akka.pattern.AskableActorRef;
import akka.util.Timeout;
import com.shu.meter.MeterDemoActor;
import pojo.Login;
import pojo.Logout;
import pojo.Meter;
import pojo.MeterRequest;
import scala.Option;
import scala.PartialFunction;
import scala.concurrent.Await;
import scala.concurrent.Future;

import static scala.compat.java8.FutureConverters.toJava;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/10 14:52
 * @version: 1.0
 */
public class TerminalDemoActor extends AbstractActor {

    /**
     * 自定义监督策越
     */
    private static SupervisorStrategy strategy = new OneForOneStrategy(
                    10,
                    Duration.ofMinutes(1),
                    DeciderBuilder
                            .match(ArithmeticException.class, e -> (SupervisorStrategy.Directive) SupervisorStrategy.resume())
                            .match(NullPointerException.class, e -> (SupervisorStrategy.Directive) SupervisorStrategy.restart())
                            .match(IllegalArgumentException.class, e -> (SupervisorStrategy.Directive) SupervisorStrategy.stop())
                            .matchAny(o -> (SupervisorStrategy.Directive) SupervisorStrategy.escalate())
                            .build());


    /**
     * 在线状态
     */
    private Boolean Online;

    /**
     * 在构造函数之后调用 ,可以完成一些初始化
     *
     * @throws Exception
     * @throws Exception
     */
    @Override
    public void preStart() throws Exception, Exception {
        super.preStart();
        System.out.println("Life 初始化");
    }

    /**
     * 在重启之前调用
     *
     * @throws Exception
     * @throws Exception
     */
    @Override
    public void postStop() throws Exception, Exception {
        super.postStop();
        System.out.println("Life 即将重启");
    }


    /**
     * 要注意的是preRestart和postRestart只在重启的时候才会被调用。它们默认调用了preStart和postStop，但是调用它们的时候就不再直接调用preStart和postStop了。
     *
     * @param reason
     * @param message
     * @throws Exception
     * @throws Exception
     */
    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception, Exception {
        super.preRestart(reason, message);
        System.out.println("Life 即将重启 调用preStart初始化");
    }


    /**
     * 自定义策越
     * @return
     */
    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }

    /**
     * 收到小消息
     *
     * @return
     */
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                // 连接成功
                .match(Login.class, x -> {
                    // 在线状态改变
                    setOnline(true);
                    // 回应消息,登录成功
                    sender().tell(1001, self());
                    System.out.println("收到登录请求");
                })
                // 连接成功
                .match(Logout.class, x -> {
                    // 在线状态改变
                    setOnline(false);
                    // 回应消息,登录成功
                    sender().tell(1002, self());
                    System.out.println("收到注销请求");
                })
                // 请求数据
                .match(MeterRequest.class, msg -> {
                    // 在线
                    if (getOnline()) {
                        // 获取消息
                        Future sFuture = new AskableActorRef(context().actorOf(Props.create(MeterDemoActor.class))).ask(msg,Timeout.apply(1000,TimeUnit.SECONDS) );
                        CompletionStage<Meter> cs = toJava(sFuture);
                        CompletableFuture<Meter> future = (CompletableFuture<Meter>) cs;
                        // 消息发送给客服端
                        if (future.get() != null) {
                            sender().tell(future.get(), self());
                        }
                    }
                })
                // 未找到消息
                .matchAny(o ->
                        sender().tell(new Status.Failure(new ClassNotFoundException()), self())
                )
                .build();

    }


    public Boolean getOnline() {
        return Online;
    }

    public void setOnline(Boolean online) {
        Online = online;
    }




}
