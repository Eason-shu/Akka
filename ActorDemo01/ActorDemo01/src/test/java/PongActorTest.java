import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.AskableActorRef;
import akka.util.Timeout;
import org.junit.Test;
import scala.concurrent.Future;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static scala.compat.java8.FutureConverters.toJava;


/**
 * @description:
 * @author: shu
 * @createDate: 2022/11/27 18:49
 * @version: 1.0
 */
public class PongActorTest {
    ActorSystem system = ActorSystem.create();
    ActorRef actorRef = system.actorOf(Props.create(JavaPongActor.class));


    /**
     * 成功测试
     * @throws Exception
     */
    @Test
    public void shouldReplyToPingWithPong() throws Exception {
        final CompletableFuture<String> jFuture =
                (CompletableFuture<String>) askPong("Ping");
        assert (jFuture.get(1000, TimeUnit.MILLISECONDS)
                .equals("Pong"));
         //打印获取的值
        System.out.println("获取的信息："+jFuture.get());
    }


    /**
     * 失败测试
     * @throws Exception
     */
    @Test
    public void shouldReplyToUnknownMessageWithFailure() throws
            Exception {

        final CompletableFuture<String> jFuture =
                (CompletableFuture<String>) askPong("unknown");
        jFuture.get(1000, TimeUnit.MILLISECONDS);
    }


    /**
     *  thenAccept测试
     * @throws Exception
     */
    @Test
    public void printToConsole() throws Exception {
        askPong("Ping").
                thenAccept(x -> System.out.println("replied with: " + x));
        Thread.sleep(100);
    }


    /**
     * 处理结果测试
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void CoverResult() throws ExecutionException, InterruptedException {
        // 将处理结果转为大写
        CompletableFuture<String> future = (CompletableFuture<String>) askPong("Ping").thenApply(x -> x.toUpperCase(Locale.ROOT));
        System.out.println(future.get());
    }

    /**
     * 异步转换结果
     */
    @Test
    public void AsyncCoverResult(){
        CompletionStage<CompletionStage<String>> futureFuture =
                askPong("Ping").thenApply(this::askPong);
    }


    /**
     * 错误处理
     */
    @Test
    public void ErrorResult(){
        askPong("cause error").handle((x, t) -> {
            if(t!=null){
                System.out.println("Error: " + t);
            }
            return null;
        });
    }


    /**
     * 失败默认值
     */
    @Test
    public void Recover(){
        CompletionStage<String> cs = askPong("cause error")
                .exceptionally(t -> {
                    return "default";
                });
    }


    @Test
    public void CompletableFuture() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = (CompletableFuture<String>)  askPong("Ping")
                .thenCombine(askPong("Ping"), (a, b) -> {
                    return a + b; //"PongPong"
                });
        System.out.println(future.get());
    }



    /**
     * 抽离成公共类
     * @param message
     * @return
     */
    public CompletionStage<String> askPong(String message){
        Future sFuture = new AskableActorRef(actorRef).ask(message, Timeout.apply(1000));
        CompletionStage<String> cs = toJava(sFuture);
        return cs;
    }
}



