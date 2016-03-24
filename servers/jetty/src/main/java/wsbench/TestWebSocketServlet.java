package wsbench;

import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;

public class TestWebSocketServlet extends WebSocketServlet {

  @Override
  public void configure(WebSocketServletFactory factory) {
    factory.register(EventSocket.class);
  }
}

class EventSocket extends WebSocketAdapter {

  protected Session session;

  @Override
  public void onWebSocketConnect(Session session) {
    this.session = session;
  }

  @Override
  public void onWebSocketText(String message) {
    try {
      session.getRemote().sendString(message);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
