package article;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import akka.pattern.AskableActorSelection;
import akka.util.Timeout;
import org.jboss.netty.handler.codec.http.HttpResponse;
import pojo.GetRequest;
import static scala.compat.java8.FutureConverters.toJava;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @description: 文章解析Ask解析
 * @author: shu
 * @createDate: 2022/12/7 20:50
 * @version: 1.0
 */
public class AskDemoArticleParser extends AbstractActor {

    /**
     * 缓存Actor路径
     */
    private final ActorSelection cacheActor;
    /**
     * HttpActro 路径
     */
    private final ActorSelection httpClientActor;
    /**
     * 文章实际解析路径
     */
    private final ActorSelection artcileParseActor;
    /**
     * 超时时间
     */
    private final Timeout timeout;
    /**
     *  当前Actor
     */
    final ActorRef senderRef = sender();


    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    /**
     * 构造器
     * @param cacheActorPath 文章html解析缓存路径
     * @param httpClientActorPath http请求路径
     * @param artcileParseActorPath 文章解析actor路径
     * @param timeout 超时设置
     */
    public AskDemoArticleParser(String cacheActorPath, String httpClientActorPath, String artcileParseActorPath, Timeout timeout) {
        this.cacheActor = context().actorSelection(cacheActorPath);
        this.httpClientActor = context().actorSelection(httpClientActorPath);
        this.artcileParseActor = context().actorSelection(artcileParseActorPath);
        this.timeout = timeout;
    }


    /**
     * 消息的监听
     * @return
     */
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(ParseArticle.class, msg -> {
                    // 第一步先走缓存中查询是否有数据
                    final CompletionStage cacheResult =
                            toJava(new AskableActorSelection(cacheActor).ask( new GetRequest(msg.getUrl()), timeout));
                    log.info("缓存中的数据：{}"+cacheResult);
                    // 第二步：缓存中没用的话，就解析实时数据
                    final CompletionStage result = cacheResult.handle((x, t) -> { return (x!= null)
                            ? CompletableFuture.completedFuture(x) :
                            // HTTP客户端的Actor发送ask请求，请求获取数据
                            toJava(new AskableActorSelection(httpClientActor).ask( msg.getUrl(), timeout))
                                    .thenCompose(rawArticle -> toJava(
                                            // 向用于文章解析的Actor发送ask请求，请求对原始文章进行解析。
                                            new AskableActorSelection(artcileParseActor).ask(rawArticle, timeout))
                                        );
                            }).thenCompose(x -> x);
                    log.info("实时处理的数据：{}"+cacheResult);
                    // 处理解析内容
                    result.handle((x, t) -> {
                        if(x != null) {
                            if(x instanceof String)
                                senderRef.tell(x, self());
                        } else if( x == null )
                            senderRef.tell(new akka.actor.Status.Failure((Throwable)t), self());
                        return null;
                    });
                }).build();
    }

}

