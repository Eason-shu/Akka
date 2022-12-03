import akka.actor.{ActorSystem, Props}

/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/1 11:41
 * @version: 1.0
 */
object Main extends App {
  val system = ActorSystem("akkademy")
  system.actorOf(Props[ScalaRequest], name = "akkademy-db")
}