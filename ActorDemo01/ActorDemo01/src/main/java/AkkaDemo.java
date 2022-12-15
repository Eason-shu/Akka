import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * @description: UntypedActor基于经典的Actor模型实现，能完整表达Akka-Actor的设计思想，推荐使用它来定义Actor。
 * @author: shu
 * @createDate: 2022/12/15 10:34
 * @version: 1.0
 */
public class AkkaDemo extends UntypedActor {

    private LoggingAdapter log = Logging.getLogger( this .getContext().system(), this);

    /**
     * 该Actor下的子Actor，受到当前Actor的监督
     */
    ActorRef childActor=getContext().actorOf(Props.create(JavaPongActor.class), "childActor" );


    /** 工厂模式Props
     * @param response
     * @return
     */
    public static Props props(String response) {
        return Props.create(AkkaDemo.class, response);
    }

    /**
     * 收到消息
     * @param msg
     * @throws Exception
     * @throws Exception
     */
    @Override
    public void onReceive(Object msg) throws Exception, Exception {
        if (msg instanceof String) {
            log.info( msg.toString());
        } else {
            unhandled( msg);
        }
    }




    /**
     * 创建ActorRef实例
     * @param args
     */
    public static void main(String[] args) {
        //参数为ActorSystem的名字，可以不传
        ActorSystem system =ActorSystem.create( "sys" );
        //参数分别是构造器和Actor的名字，名字可以不传
        ActorRef actorRef =system.actorOf(Props.create(AkkaDemo.class), "actorDemo" );
    }
}


