import akka.actor.UntypedActor;

/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/15 11:19
 * @version: 1.0
 */
class TargetActor extends UntypedActor {
    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println("target receive: "+msg);
    }
}
