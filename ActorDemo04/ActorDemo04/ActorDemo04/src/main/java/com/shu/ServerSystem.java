package com.shu;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.shu.meter.MeterDemoActor;
import com.shu.terminal.TerminalDemoActor;

/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/10 18:11
 * @version: 1.0
 */
public class ServerSystem {
    public static void main(String[] args) {
        // Create an Akka system
        ActorSystem system = ActorSystem.create("terminal");
        // Create an actor
        ActorRef ref = system.actorOf(Props.create(TerminalDemoActor.class), "terminal-server");
        System.out.println(ref);

    }
}
