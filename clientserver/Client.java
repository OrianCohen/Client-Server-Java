package clientserver;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // In order to request something over TCP from a server, we need a port number and an IP address
        Socket socket = new Socket("127.0.0.1",8010);
        // socket is an abstraction of 2-way data pipe
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        // use decorators
        ObjectInputStream fromServer;
        fromServer = new ObjectInputStream(inputStream);
        ObjectOutputStream toServer = new ObjectOutputStream(outputStream);

        int[][] source = {
                {1, 1, 1},
                {0, 1, 0},
                {0, 1, 1}
        };

        toServer.writeObject("Task Four");
        toServer.writeObject(source);
        toServer.writeObject(new Index(0,0));
        toServer.writeObject(new Index(1,1));
        ArrayList<List<Index>> AllExistingPaths = new ArrayList<>((ArrayList<List<Index>>)fromServer.readObject());
        System.out.println("\n TaskFour - All existing routes between 2 points: ");
        for(List<Index> path: AllExistingPaths){
            System.out.println(path);
        }

//        toServer.writeObject("Task Three");
//        toServer.writeObject(source);


        toServer.writeObject("Task Two");
        toServer.writeObject(source);
        toServer.writeObject(new Index(0,0));
        toServer.writeObject(new Index(2,2));
        ArrayList<List<Index>> TheShortestPath = new ArrayList<>((ArrayList<List<Index>>)fromServer.readObject());
        System.out.println("\n TaskTwo - The most shortest paths: ");
        int minPathSize= source.length * source.length;
        for(List<Index> path: TheShortestPath){
            if(path.size() < minPathSize)
                minPathSize = path.size();
        }
        for(List<Index> pathlist: TheShortestPath){
            if(pathlist.size() == minPathSize)
                System.out.println(pathlist);
        }


        toServer.writeObject("Task One");
        toServer.writeObject(source);
        ArrayList <HashSet<Index>> connectedComponents = new ArrayList<>((ArrayList<HashSet<Index>>)fromServer.readObject());
        System.out.println("\n TaskOne - All the connected components are: ");
        for(Object k : connectedComponents)
        {
            System.out.println(k);
        }

        toServer.writeObject("stop");
        fromServer.close();
        toServer.close();
        socket.close();
        System.out.println("\n All streams are closed");


    }
}
