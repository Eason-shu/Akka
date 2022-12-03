import scala.concurrent.duration.DurationInt
import scala.concurrent.Await
import scala.language.postfixOps

/**
 * @description:
 * @author: shu
 * @createDate: 2022/12/1 11:44
 * @version: 1.0
 */

object Main extends App {
  val client = new SClient("127.0.0.1:2552")
  client.set("123", new Integer(123))
  val futureResult = client.get("123")
  val result = Await.result(futureResult, 10  seconds)

}





