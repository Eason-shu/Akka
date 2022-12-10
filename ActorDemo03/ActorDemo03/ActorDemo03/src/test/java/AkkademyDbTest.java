import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import client.JClient;
import client.TerminalClient;
import org.junit.Test;
import pojo.Meter;
import pojo.SetRequest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class AkkademyDbTest {




    /**
     * 测试注意需要放在不同的两个项目进行测试，不然会Caused by: java.net.BindException: Address already in use: bind
     * @throws Exception
     */
    @Test
    public void itShouldSetRecord() throws Exception {
//            JClient client = new JClient("127.0.0.1:2552");
//                client.set("123", 123);
//                Integer result = (Integer) ((CompletableFuture) client.
//                        get("123")).get();
//                System.out.println("获取的结果："+result);
//                assert(result == 123);

        TerminalClient client = new TerminalClient("127.0.0.1:2552");
        // 发送登录
        int login = (int) ((CompletableFuture) client.sendLogin()).get();
        // 成功获取电表消息
        if(login==1001){
            System.out.println("返回的结果"+login);
            Meter meter = (Meter) ((CompletableFuture) client.getMeterInfo("123", 2501001)).get();
            System.out.println("获取到的电表信息"+meter);
            if(meter!=null){
                int logout = (int) ((CompletableFuture) client.sendLogout()).get();
                if(logout==1002){
                    System.out.println("返回的结果"+login);
                    System.out.println("下线成功");
                }
            }
        }
    }


}