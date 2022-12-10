package article;

import akka.actor.*;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import akka.util.Timeout;
import org.jboss.netty.handler.codec.http.HttpResponse;
import pojo.GetRequest;
import pojo.SetRequest;

import java.util.concurrent.TimeoutException;


/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/9 20:07
 * @version: 1.0
 */
public class TellDemoArticleParse  extends AbstractActor {
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
     * 当前Actor
     */
    final ActorRef senderRef = sender();


    LoggingAdapter log = Logging.getLogger(getContext().system(), this);


    public TellDemoArticleParse(ActorSelection cacheActor, ActorSelection httpClientActor, ActorSelection artcileParseActor, Timeout timeout) {
        this.cacheActor = cacheActor;
        this.httpClientActor = httpClientActor;
        this.artcileParseActor = artcileParseActor;
        this.timeout = timeout;
    }






    @Override
    public Receive createReceive() {

        return  ReceiveBuilder.create()
                .match(ParseArticle.class, msg -> {
                    // 运程Actor
                    ActorRef extraActor = buildExtraActor(sender(), msg.getUrl());
                    // 缓存Actor
                    cacheActor.tell(new ParseArticle(msg.getUrl()), extraActor);
                    // httpActor
                    httpClientActor.tell(msg.getUrl(), extraActor);

                    context().system().scheduler().scheduleOnce(
                            timeout.duration(),
                            extraActor,
                            "timeout",
                            context().system().dispatcher(),
                            ActorRef.noSender()
                    );
                }).build();
    }


    //Java
    private ActorRef buildExtraActor(ActorRef senderRef, String uri) {

        class MyActor extends AbstractActor {
            public MyActor() {
            }

            @Override
            public Receive createReceive() {
                return ReceiveBuilder.create()

                        .matchEquals(String.class, x ->
                                //if we get timeout, then fail
                                x.equals("timeout"), x -> {
                            senderRef.tell(
                                    new Status.Failure(
                                            new TimeoutException("timeout! ")
                                    ),
                                    self()
                            );
                            context().stop(self());
                        })

                        .match(HttpResponse.class, httpResponse -> {
                            artcileParseActor.tell(
                                    new ParseArticle(uri),
                                    self()
                            );
                        })

                        .match(String.class, body -> {
                            //The cache response will come back before
                            //the HTTP response so we never parse in this case.
                            senderRef.tell(body, self());
                            context().stop(self());
                        })

                        .match(ParseArticle.class, articleBody -> {
                            cacheActor.tell(
                                    new SetRequest(articleBody.getUrl(), "收到了消息"),
                                    self()
                            );
                            senderRef.tell("收到了消息", self());
                            context().stop(self());
                        })


                        .build();
            }
        }

        return context().actorOf(Props.create(MyActor.class,()->new MyActor()));
    }
}

