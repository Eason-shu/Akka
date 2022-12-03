import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import client.JClient;
import org.junit.Test;
import pojo.SetRequest;

import java.util.concurrent.CompletableFuture;
public class AkkademyDbTest {




    /**
     * 测试注意需要放在不同的两个项目进行测试，不然会Caused by: java.net.BindException: Address already in use: bind
     * @throws Exception
     */
    @Test
    public void itShouldSetRecord() throws Exception {
            JClient client = new JClient("127.0.0.1:2552");
                client.set("123", 123);
                Integer result = (Integer) ((CompletableFuture) client.
                        get("123")).get();
                System.out.println("获取的结果："+result);
                assert(result == 123);

        }


}