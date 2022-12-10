package client;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.AskableActorSelection;
import akka.util.Timeout;
import pojo.*;

import java.util.Date;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static scala.compat.java8.FutureConverters.toJava;

/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/10 18:22
 * @version: 1.0
 */
public class TerminalClient {
    private final ActorSystem system = ActorSystem.create("LocalSystem");

    private final ActorSelection remoteTerminal;

    public TerminalClient(String remoteAddress) {
        remoteTerminal = system.actorSelection("akka.tcp://terminal@" +
                remoteAddress+ "/user/terminal-server");
    }


    /**
     * 获取消息
     * @param key
     * @param value
     * @return
     */
    public CompletionStage getMeterInfo(String key, int value) {
        System.out.println(remoteTerminal);
        return toJava(new AskableActorSelection(remoteTerminal).ask(new MeterRequest(key, value), Timeout.apply(5000, TimeUnit.SECONDS)));
    }


    /**
     * 上线
     * @return
     */
    public CompletionStage sendLogin() {
        System.out.println(remoteTerminal);
        return toJava(new AskableActorSelection(remoteTerminal).ask(new Login("1001", new Date()), Timeout.apply(5000, TimeUnit.SECONDS)));
    }


    /**
     * 下线
     * @return
     */
    public CompletionStage sendLogout() {
        System.out.println(remoteTerminal);
        return toJava(new AskableActorSelection(remoteTerminal).ask(new Logout("1001", new Date()), Timeout.apply(5000, TimeUnit.SECONDS)));
    }


}
