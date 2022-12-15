import akka.actor.ActorNotFound;
import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/15 11:33
 * @version: 1.0
 */
public class SearchTest {
    public static void main(String[] args) {
        ActorSystem system =ActorSystem.create( "sys" );
        ActorSelection as = system.actorSelection("/user/lookupActor/targetActor");
        Timeout timeout = new Timeout(Duration.create(2, "seconds"));
        Future<ActorRef> fu = as.resolveOne(timeout);
        fu.onSuccess(new OnSuccess<ActorRef>() {
            @Override
            public void onSuccess(ActorRef ref) throws Throwable {
                System.out.println("查找到Actor:" + ref);
            }
        }, system.dispatcher());
        fu.onFailure(new OnFailure() {
            @Override
            public void onFailure(Throwable ex) throws Throwable {
                if(ex instanceof ActorNotFound) {
                    System.out.println("没找到Actor:"+ex.getMessage());
                }
            }
        }, system.dispatcher());
    }
}
