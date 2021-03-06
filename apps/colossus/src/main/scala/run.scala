package wsbench

import akka.actor.ActorSystem
import colossus.IOSystem

object run {

  def main(args: Array[String]): Unit = {
    implicit val actorSystem = ActorSystem("COLOSSUS")
    implicit val ioSystem = IOSystem()

    val websocketServer = WebsocketExample.start(8080)
  }

}


import colossus._
import colossus.core._
import colossus.protocols.websocket._
import subprotocols.rawstring._

import akka.actor._

import scala.concurrent.duration._

class PrimeGenerator extends Actor {

  var lastPrime = 1

  def receive = {
    case Next => sender() ! nextPrime
  }

  def nextPrime = {
    var nextPrime = lastPrime
    var prime = false
    while (!prime) {
      nextPrime += 1
      var n = 1
      var ok = true
      while (n < nextPrime - 1 && ok) {
        n += 1
        if (nextPrime % n == 0) {
          ok = false
        }
      }
      prime = ok
    }
    lastPrime = nextPrime
    nextPrime
  }
}

case object Next



object WebsocketExample {

  val large = (0 to 100000).mkString

  def start(port: Int)(implicit io: IOSystem) = {

    val generator = io.actorSystem.actorOf(Props[PrimeGenerator])

    WebsocketServer.start("127.0.0.1", port, "/ws"){worker => new WebsocketInitializer(worker) {

      def onConnect = ctx => new WebsocketServerHandler[RawString](ctx) with ProxyActor {
        private var sending = false

        override def preStart() {
          println("123")
          sendMessage("HELLO THERE!")
        }

        override def shutdown() {
          sendMessage("goodbye!")
          super.shutdown()
        }

        def handle = {
          case "START" => {
            sending = true
            generator ! Next
          }
          case "STOP" => {
            sending = false
          }
          case "LARGE" => {
            sendMessage(large)
          }
          case "MANY" => {
            //send one message per event loop iteration
            def next(i: Int) {
              if (i > 0) sendMessage(i.toString){_ => next(i - 1)}
            }
            next(1000)
          }
          case "EXIT" => {
            disconnect()
          }
          case other => {
            sendMessage(s"unknown command: $other")
          }
        }

        def handleError(reason: Throwable){}

        def receive = {
          case prime: Integer => {
            sendMessage(s"PRIME: $prime")
            if(sending) {
              import io.actorSystem.dispatcher
              io.actorSystem.scheduler.scheduleOnce(100.milliseconds, generator , Next)
            }
          }
        }

      }

    }}

  }
}
