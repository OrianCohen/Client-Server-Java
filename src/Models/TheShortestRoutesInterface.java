package src.Models;

import src.BackEnd.Index;

import java.util.ArrayList;
import java.util.List;

public interface TheShortestRoutesInterface {
    void printAllPaths(int[][] primitiveMatrix, Index start, Index end);
    void printAllPathsUtil(Index u, Index d, boolean[][] visited,List<Index> localPathList);
    ArrayList<List<Index>> GetTheShortestRoutes(ArrayList<List<Index>> AllRoutes, int[][] source);
    boolean ValidMatrix(int[][] Array);
}
