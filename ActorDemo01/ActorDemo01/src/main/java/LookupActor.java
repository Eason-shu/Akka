import akka.actor.*;

/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/15 11:19
 * @version: 1.0
 */
class LookupActor extends UntypedActor {

    private ActorRef target = null;
    {
        target = getContext().actorOf(Props.create(TargetActor.class), "targetActor");
    }


    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof String) {
            if ("find".equals(msg)) {
                ActorSelection as = getContext().actorSelection("targetActor");
                as.tell(new Identify("A001"), getSelf());
            }
        }
        // ActorIdentity 系统默认消息
        else if (msg instanceof ActorIdentity) {
            ActorIdentity ai = (ActorIdentity) msg;
            if (ai.correlationId().equals("A001")) {
                ActorRef ref=ai.getRef();
                if(ref!=null) {
                    System.out.println("ActorIdentity: " + ai.correlationId() + ""+ ref);
                    ref.tell("hello target", getSelf());
                }
            }

        } else {
            unhandled(msg);
        }
    }

    public static void main(String[] args) {
        //参数为ActorSystem的名字，可以不传
        ActorSystem system =ActorSystem.create( "sys" );
        //参数分别是构造器和Actor的名字，名字可以不传
        ActorRef actorRef =system.actorOf(Props.create(LookupActor.class), "actorDemo" );
        // 发送消息
        actorRef.tell("find",ActorRef.noSender());
    }
}
