package wsbench

import java.net.InetAddress

import akka.actor.{Actor, Props, ActorSystem}
import org.mashupbots.socko.events.{WebSocketFrameEvent, HttpRequestEvent}
import org.mashupbots.socko.routes._
import org.mashupbots.socko.webserver.{WebLogConfig, WebServer, WebServerConfig}

object Start extends App {

  implicit val system = ActorSystem()

  val routes = Routes({
    case HttpRequest(httpRequest) => httpRequest match {
      case GET(Path("/http")) => system.actorOf(Props[DefaultHandler]) ! httpRequest
    }

    case WebSocketHandshake(wsHandshake) => wsHandshake match {
      case Path("/ws") => wsHandshake.authorize()
    }

    case WebSocketFrame(wsFrame) => system.actorOf(Props[DefaultHandler]) ! wsFrame
  })

  class DefaultHandler extends Actor {
    def receive = {
      case event: HttpRequestEvent =>
        event.response.write("index", "text/html")
        context.stop(self)

      case event: WebSocketFrameEvent =>
        event.writeText(event.readText())
        context.stop(self)
    }
  }

  val webServer = new WebServer(WebServerConfig(hostname = "0.0.0.0", port = 8080, webLog = Some(WebLogConfig())), routes, system)
  webServer.start()
  println(s"Socko Web started")
}