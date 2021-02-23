package src.algorithms.interfaces;

import src.algorithms.Index;
import src.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.List;

public interface TheShortestRoutesInterface {
    void printAllPaths(int[][] primitiveMatrix, Index start, Index end) throws InvalidInputException;

    void printAllPathsUtil(Index u, Index d, boolean[][] visited, List<Index> localPathList);

    ArrayList<List<Index>> GetTheShortestRoutes(ArrayList<List<Index>> AllRoutes, int[][] source);

    boolean ValidMatrix(int[][] Array) throws InvalidInputException;

    ArrayList<List<Index>> getAllRoutes();

}
