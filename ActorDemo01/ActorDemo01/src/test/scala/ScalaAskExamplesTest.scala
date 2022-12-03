/**
 * @description:
 * @author: shu
 * @createDate: 2022/11/27 19:44
 * @version: 1.0
 */

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import org.scalatest.{FunSpecLike, Matchers}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.language.postfixOps
class ScalaAskExamplesTest extends FunSpecLike with Matchers {

   val system: ActorSystem = ActorSystem()
   implicit val timeout: Timeout = Timeout(5 second)
   val pongActor: ActorRef = system.actorOf(Props(classOf[ScalaPongActor]))


  describe("Pong actor") {

    // 正确测试
    it("should respond with Pong") {
      val future = pongActor ? "Ping" //uses the implicit timeout
      val result = Await.result(future.mapTo[String], 1 second)
      assert(result == "Pong")
      println(result)
//      (pongActor ? "Ping").onSuccess{
//        case x: String => println("replied with: " + x)
//
//      }
    }

    // 失败测试
    it("should fail on unknown message") {
      val future = pongActor ? "unknown"
      intercept[Exception]{
        Await.result(future.mapTo[String], 1 second)
      }
      println( Await.result(future.mapTo[String], 1 second))
    }

    // 成功处理测试
    it("should print to console"){
      (pongActor ? "Ping").onSuccess({
        case x: String => println("replied with: " + x)
      })
      Thread.sleep(100)
    }

    it("结果处理测试"){
      val result= askPong("Ping").map(_.toUpperCase())
      println(Await.result(result.mapTo[String], 1 second));
    }

    it("组合结果测试"){
      val f1 =  askPong("Ping")
      val f2 =  askPong("Ping")
      val futureAddition: Future[String] = {
        for (
          res1 <- f1;
          res2 <- f2
        ) yield res1 + res2
      }
      println(Await.result(futureAddition.mapTo[String], 1 second));
    }

    // 返回列表
    it("Future list"){
      // list
      val listOfFutures: List[Future[String]] = List("Ping", "Ping",
        "failed").map(x => askPong(x))
      // 转换结果为list
      val futureOfList: Future[List[String]] = Future.sequence(listOfFutures)
      futureOfList.foreach(x=> println(x))

    }
  }


  /**
   * 公共类
   * @param message
   * @return
   */
  def askPong(message: String): Future[String] = (pongActor ? message).mapTo[String]
}
