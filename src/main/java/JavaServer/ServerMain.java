package JavaServer;

import JavaServer.config.Configuration;
import JavaServer.config.ConfigurationManager;

public class ServerMain {
    public static void main(String[] args) {

        System.out.println("Server starting...");

        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println("Using port: " + conf.getPort());
        System.out.println("Using webroot: " + conf.getWebroot());


        NIOServer nioServer = new NIOServer();
        nioServer.start(conf.getPort());
    }
}
