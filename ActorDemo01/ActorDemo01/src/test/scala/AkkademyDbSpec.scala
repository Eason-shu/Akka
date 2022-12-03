
import akka.actor.ActorSystem
import akka.testkit.TestActorRef

import org.scalatest.{FunSpecLike, Matchers}


class  AkkademyDbSpec extends FunSpecLike with Matchers {
  // 获取系统实例
  implicit val system = ActorSystem()

  describe("akkademyDb") {
    describe("given SetRequest") {

      // 测试1
      it("should place key/value into map") {
        // 创建Actor实例
        val actorRef = TestActorRef(new AkkaDba)
        // 发送消息
        actorRef ! SetRequest("key", "123456")
        // 验证消息
        val akkademyDb = actorRef.underlyingActor
        akkademyDb.map.get("key") should equal("123456")
      }

      //
      it("PONG TEST"){
        val actorRef = TestActorRef(new ScalaPongActor)
        actorRef ! ("Ping")
      }
    }
  }
}