package JavaServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.util.HashSet;

public class NIOServer {

    /**
     * @param portNumber Port for server to listen on
     */
    public void start(final int portNumber) {
        String html = "<html><head><title>Java Server</title></head><body>This page was served using " +
                "my Java NIO HTTP server</body></html>";
        String response = "HTTP/1.1 200 OK\r\n" +                       // HTTP version response code
                "Content-Type: text/html; charset=utf-8\r\n" +
                "Content-Length: " + html.getBytes().length + "\r\n" +  // header
                "\r\n" +
                html
                + "\r\n" + "\r\n";
        var clients = new HashSet<SocketChannel>();
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(portNumber));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            var buffer = ByteBuffer.allocate(1024);
            while (true) {
                if (selector.select() == 0) {
                    continue;
                }
                for (var key : selector.selectedKeys()) {
                    // Accept connections
                    if (key.isAcceptable()) {
                        if (key.channel() instanceof ServerSocketChannel channel) {
                            var client = channel.accept();
                            var socket = client.socket();
                            var clientInfo = socket.getInetAddress().getHostAddress() + ':' + socket.getPort();
                            System.out.println("CONNECTED: " + clientInfo);
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                            clients.add(client);
                        }
                    } else if (key.isReadable()) {
                        if (key.channel() instanceof SocketChannel client) {
                            client.read(buffer);
                            String clientResponse = new String(buffer.array());
                            System.out.println(clientResponse);
                            buffer.flip();

                            buffer.clear();
                            buffer.put(response.getBytes(), 0, response.length());
                            buffer.flip();
                            client.write(buffer);
                            buffer.clear();
                        } else {
                            throw new RuntimeException("Unknown channel");
                        }
                        client.close();
                        clients.remove(client);
                    }

                }

                selector.selectedKeys().clear();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            for (var client : clients) {
                try {
                    client.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
