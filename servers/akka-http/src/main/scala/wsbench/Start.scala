package wsbench

import java.net.InetAddress

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.ws.{UpgradeToWebSocket, TextMessage, Message}
import akka.stream.scaladsl._
import akka.stream.ActorMaterializer
import akka.http.scaladsl.model.HttpMethods._

object Start extends App {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val websocketService =
    Flow[Message].collect {
      case msg: TextMessage ⇒ TextMessage(msg.textStream)
    }

  val requestHandler: HttpRequest => HttpResponse = {
    case HttpRequest(GET, Uri.Path("/http"), _, _, _) =>
      HttpResponse(entity = HttpEntity("index"))

    case r @ HttpRequest(GET, Uri.Path("/ws"), _, _, _) =>
      r.header[UpgradeToWebSocket] match {
        case Some(upgrade) ⇒ upgrade.handleMessages(websocketService)
        case None          ⇒ HttpResponse(400, entity = "Not a valid websocket request!")
      }

    case _: HttpRequest =>
      HttpResponse(404, entity = "Unknown resource!")
  }

  val hostAddress = InetAddress.getLocalHost.getHostAddress
  val serverSource = Http().bind(interface = "0.0.0.0", port = 8080)

  serverSource.to(Sink.foreach { connection =>
    connection handleWithSyncHandler requestHandler
  }).run()
  println(s"Akka Http started")
}