package wsbench;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ws")
class Endpoint {

  @OnMessage
  String text(String t) {
    return t;
  }
}