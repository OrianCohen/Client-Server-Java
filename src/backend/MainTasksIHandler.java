package src.backend;

import src.backend.interfaces.IHandler;
import src.algorithms.*;
import src.algorithms.interfaces.TheShortestRoutesInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class MainTasksIHandler implements IHandler {
    private ObjectOutputStream mObjectOutputStream;
    private ObjectInputStream objectInputStream;
    private boolean mIsRunning = true;
    private static final String STOP = "stop";
    private static final String TASK_ONE = "Task One";
    private static final String TASK_TWO = "Task Two";
    private static final String TASK_THREE = "Task Three";
    private static final String TASK_FOUR = "Task Four";

    @Override
    public void handle(InputStream inClient, OutputStream outClient) throws Exception {
        initReaderAndOutput(outClient, inClient);
        while (mIsRunning) {
            switch (objectInputStream.readObject().toString()) {
                case STOP: {
                    mIsRunning = false;
                    break;
                }
                case TASK_ONE: {
                    int[][] primitiveMatrix = (int[][]) objectInputStream.readObject();
                    ArrayList<HashSet<Index>> component = new ConnectedComponent().findOneReachables(primitiveMatrix);
                    mObjectOutputStream.writeObject(component);
                    break;
                }
                case TASK_TWO: {
                    executeBFSTask(2);
                    break;
                }
                case TASK_THREE: {
                    int[][] primitiveMatrix = (int[][]) objectInputStream.readObject();
                    int component = new SubMarineAlgorithm().subMarineGame(primitiveMatrix);
                    mObjectOutputStream.writeObject(component);
                    break;
                }

                case TASK_FOUR: {
                    executeBFSTask(4);
                    break;
                }
            }
        }

    }

    private void initReaderAndOutput(OutputStream outClient, InputStream inClient) throws IOException {
        mObjectOutputStream = new ObjectOutputStream(outClient);
        objectInputStream = new ObjectInputStream(inClient);
    }

    private void executeBFSTask(int number) throws Exception {
        TheShortestRoutesInterface allRoutes = new BFSAlgorithm();
        int[][] primitiveMatrix = (int[][]) objectInputStream.readObject();
        Index start = (Index) objectInputStream.readObject();
        Index end = (Index) objectInputStream.readObject();

        switch (number) {
            case 2: {
                if (allRoutes.ValidMatrix(primitiveMatrix)) {
                    allRoutes.printAllPaths(primitiveMatrix, start, end);
                    mObjectOutputStream.writeObject(allRoutes.GetTheShortestRoutes(allRoutes.getAllRoutes(), primitiveMatrix));
                }
                break;
            }
            case 4: {
                allRoutes.printAllPaths(primitiveMatrix, start, end);
                mObjectOutputStream.writeObject(allRoutes.getAllRoutes());
                break;
            }
        }
    }
}