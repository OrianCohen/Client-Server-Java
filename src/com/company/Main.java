package src.com.company;

import src.BackEnd.TcpServer;
import src.ClientServer.Client;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TcpServer.main(args);
        Client.main(args);
            }
}
