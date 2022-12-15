import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/12 11:04
 * @version: 1.0
 */
public class MainSystem {
    public static void main( String[] args )
    {
        if(args.length==0)
            startup(new String[] {"2551", "2552", "0"});
        else
            startup(args);
    }

    public static void startup(String[] ports){
        ExecutorService pool = Executors.newFixedThreadPool(ports.length);
        for(String port : ports){
            pool.submit(()->{
                // Using input port to start multiple instances
                Config config = ConfigFactory.parseString(
                                "akka.remote.netty.tcp.port=" + port + "\n" +
                                        "akka.remote.artery.canonical.port=" + port)
                        .withFallback(ConfigFactory.load());

                // Create an Akka system
                ActorSystem system = ActorSystem.create("ClusterSystem", config);

                // Create an
                system.actorOf(Props.create(ClusterController.class), "ClusterListener");
            });
        }
    }
}

