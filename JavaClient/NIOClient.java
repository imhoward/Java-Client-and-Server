package JavaClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.Scanner;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void start(final int portNumber, final Scanner scanner) {
        try (var serverChannel = SocketChannel.open()) {
            serverChannel.connect(new InetSocketAddress(portNumber));
            System.out.println("Connection established!");
            var buffer = ByteBuffer.allocate(1024);
            while (true) {
                var line = scanner.nextLine();
                if (line.equalsIgnoreCase("exit")) {
                    break;
                }
                line += System.lineSeparator();
                buffer.clear().put(line.getBytes()).flip();
                while (buffer.hasRemaining()) {
                    serverChannel.write(buffer);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
