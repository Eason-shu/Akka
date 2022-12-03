package client;



import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.pattern.AskableActorSelection;
import akka.util.Timeout;
import pojo.GetRequest;
import pojo.SetRequest;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

import static scala.compat.java8.FutureConverters.toJava;


/**
 * @description:
 * @author: shu
 * @createDate: 2022/11/28 16:10
 * @version: 1.0
 */
public class JClient {
    private final ActorSystem system = ActorSystem.create("LocalSystem");
    private final ActorSelection remoteDb;

    public JClient(String remoteAddress) {
        remoteDb = system.actorSelection("akka.tcp://akkademy@" +
                remoteAddress + "/user/akkademy-db");
    }

    /**
     * 缓存消息
     * @param key
     * @param value
     * @return
     */
    public CompletionStage set(String key, Object value) {
        return toJava(new AskableActorSelection(remoteDb).ask(new SetRequest(key, value), Timeout.apply(5000, TimeUnit.SECONDS)));
    }

    /**
     * 获取缓存消息
     * @param key
     * @return
     */
    public CompletionStage get(String key){
       return   toJava(new AskableActorSelection(remoteDb).ask(new GetRequest(key), Timeout.apply(5000, TimeUnit.SECONDS)));
    }






}