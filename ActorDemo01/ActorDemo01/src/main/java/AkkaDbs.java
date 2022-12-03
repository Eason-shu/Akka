


import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

import java.util.HashMap;
import java.util.Map;


/**
 * @description: Java-AkkaDba
 * @author: shu
 * @createDate: 2022/10/27 21:13
 * @version: 1.0
 */

public class AkkaDbs extends AbstractActor {

    protected final LoggingAdapter log = Logging.getLogger(context().system(), this);
    protected final Map<String, Object> map = new HashMap<>();

    private AkkaDbs() {
        receive(ReceiveBuilder

                .match(SetRequests.class, message -> {
                    System.out.printf("收到的key:%s,value:%s%n",message.getKey(),message.getValue());
                    map.put(message.getKey(), message.getValue());
                })

                .matchAny(o ->  System.out.printf("收到的消息:%s",o))
                .build()
        );
    }
}