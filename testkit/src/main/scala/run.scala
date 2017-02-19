package testkit

import java.net.URI

import io.netty.channel.socket.SocketChannel
import io.netty.bootstrap.Bootstrap
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel._
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.http.{DefaultHttpHeaders, HttpClientCodec, HttpObjectAggregator}
import io.netty.handler.codec.http.websocketx.{TextWebSocketFrame, WebSocketClientHandshakerFactory, WebSocketFrame, WebSocketVersion}
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler
import testkit.WebSocketClientHandler

object run {

  def main(args: Array[String]): Unit = {

    val uri = new URI("ws://188.166.20.21:8080")

    val handler = new WebSocketClientHandler(WebSocketClientHandshakerFactory.newHandshaker(uri, WebSocketVersion.V00, null, true, (new DefaultHttpHeaders).add("origin", "127.0.0.1")))

    val group: EventLoopGroup = new NioEventLoopGroup
    val b: Bootstrap = new Bootstrap
    b.group(group).channel(classOf[NioSocketChannel]).handler(new ChannelInitializer[SocketChannel]() {
      protected def initChannel(ch: SocketChannel) {
        val p: ChannelPipeline = ch.pipeline
        p.addLast(new HttpClientCodec, new HttpObjectAggregator(8192), WebSocketClientCompressionHandler.INSTANCE, handler)
      }
    })
    val ch: Channel = b.connect(uri.getHost, 8080).sync.channel
    handler.handshakeFuture.sync
    val frame: WebSocketFrame = new TextWebSocketFrame("LARGE")
    ch.writeAndFlush(frame)
  }
}
