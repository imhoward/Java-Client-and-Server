package JavaServer;

public class ServerMain {
    public static void main(String[] args) {
        NIOServer nioServer = new NIOServer();
        nioServer.start(12345);
    }
}
