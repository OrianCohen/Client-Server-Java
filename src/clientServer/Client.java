package src.clientServer;

import src.algorithms.Index;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

public class Client {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // In order to request something over TCP from a server, we need a port number and an IP address
        Socket socket = new Socket("127.0.0.1", 8010);
        // socket is an abstraction of 2-way data pipe
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        // use decorators
        ObjectInputStream fromServer;
        fromServer = new ObjectInputStream(inputStream);
        ObjectOutputStream toServer = new ObjectOutputStream(outputStream);

        int[][] source = {
                {1, 1, 0},
                {0, 1, 0},
                {0, 0, 1}
        };

        toServer.writeObject("Task Four");
        toServer.writeObject(source);
        toServer.writeObject(new Index(0, 0));
        toServer.writeObject(new Index(2, 2));
        ArrayList<List<Index>> AllExistingPaths = new ArrayList<>((ArrayList<List<Index>>) fromServer.readObject());
        System.out.println("\n TaskFour - All existing routes between 2 points: ");
        for (List<Index> path : AllExistingPaths)
            System.out.println(path);

        int[][] subMarine = {
                {1, 1, 0, 0, 0},
                {1, 1, 0, 0, 1},
                {1, 1, 0, 0, 1}
        };

        toServer.writeObject("Task Three");
        toServer.writeObject(subMarine);
        int validSubmarine = (int) fromServer.readObject();
        System.out.println("\n TaskThree - The correct Submarines for this game " + validSubmarine);


        toServer.writeObject("Task Two");
        toServer.writeObject(source);
        toServer.writeObject(new Index(0, 0));
        toServer.writeObject(new Index(2, 2));
        System.out.println("\n TaskTwo - The most shortest paths: ");
        ArrayList<List<Index>> TheShortestRoutes = new ArrayList<>((ArrayList<List<Index>>) fromServer.readObject());
        for (List<Index> path : TheShortestRoutes)
            System.out.println(path);

        toServer.writeObject("Task One");
        toServer.writeObject(source);
        ArrayList<HashSet<Index>> connectedComponents = new ArrayList<>((ArrayList<HashSet<Index>>) fromServer.readObject());
        System.out.println("\n TaskOne - All the connected components are: ");
        for (HashSet<Index> obj : connectedComponents) {
            Collection<Index> sortedDFS = obj.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
            System.out.println(sortedDFS);
        }

        toServer.writeObject("stop");
        fromServer.close();
        toServer.close();
        socket.close();
        System.out.println("\n All streams are closed");


    }
}
