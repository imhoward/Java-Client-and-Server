package JavaClient;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        NIOClient client = new NIOClient();
        Scanner scanner = new Scanner(System.in);

        client.start(12345, scanner);
    }
}
