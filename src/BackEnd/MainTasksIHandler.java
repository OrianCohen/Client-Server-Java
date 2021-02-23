package src.BackEnd;

import src.Algorithms.BFSAlgorithm;
import src.Algorithms.ConnectedComponent;
import src.Algorithms.SubMarineAlgorithm;
import src.Models.ConnectedComponentInterface;
import src.Models.SubMarineAlgorithmInterface;
import src.Models.TheShortestRoutesInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class MainTasksIHandler implements IHandler {
    //Custom component
    private ConnectedComponentInterface mComponent;
    private TheShortestRoutesInterface allRoutes;
    private SubMarineAlgorithmInterface submarineGame;

    @Override
    public void handle(InputStream inClient, OutputStream outClient) throws Exception {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outClient);
        ObjectInputStream objectInputStream = new ObjectInputStream(inClient);
        boolean dowork = true;
        while (dowork) {

            switch (objectInputStream.readObject().toString()) {
                case "stop": {
                    dowork = false;
                    break;
                }

                case "Task One": {
                    mComponent = new ConnectedComponent();
                    int[][] primitiveMatrix = (int[][]) objectInputStream.readObject();
                    ArrayList<HashSet<Index>> component = mComponent.findOneReachables(primitiveMatrix);
                    objectOutputStream.writeObject(component);

                    break;
                }

                case "Task Two":{
                    allRoutes = new BFSAlgorithm();
                    BFSAlgorithm.allRoutes = new ArrayList<>();
                    int[][] primitiveMatrix = (int[][]) objectInputStream.readObject();
                    Index start = (Index) objectInputStream.readObject();
                    Index end = (Index) objectInputStream.readObject();
                    if(allRoutes.ValidMatrix(primitiveMatrix)) {
                        allRoutes.printAllPaths(primitiveMatrix, start, end);
                        objectOutputStream.writeObject(allRoutes.GetTheShortestRoutes(BFSAlgorithm.getAllRoutes(), primitiveMatrix));
                        break;
                    }
                    break;
                }

                case "Task Three": {
                    submarineGame = new SubMarineAlgorithm();
                    int[][] primitiveMatrix = (int[][]) objectInputStream.readObject();
                    int component = submarineGame.subMarineGame(primitiveMatrix);
                    objectOutputStream.writeObject(component);
                    break;
                }

                case "Task Four": {
                    allRoutes = new BFSAlgorithm();
                    BFSAlgorithm.allRoutes = new ArrayList<>();
                    int[][] primitiveMatrix = (int[][]) objectInputStream.readObject();
                    Index start = (Index) objectInputStream.readObject();
                    Index end = (Index) objectInputStream.readObject();
                    allRoutes.printAllPaths(primitiveMatrix, start, end);
                    objectOutputStream.writeObject(BFSAlgorithm.getAllRoutes());
                    break;
                }



            }
        }
    }
}