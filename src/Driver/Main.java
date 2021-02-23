package src.Driver;

import src.backend.TcpServer;
import src.clientServer.Client;

public class Main {

    public static void main(String[] args) throws Exception {
        TcpServer.main(args);
        Client.main(args);
    }
}
