package src.Algorithms;

import src.BackEnd.Index;
import src.Models.TheShortestRoutesInterface;

import java.util.ArrayList;
import java.util.List;

public class BFSAlgorithm implements TheShortestRoutesInterface {
    public Matrix matrix;
    public static ArrayList<List<Index>> allRoutes = new ArrayList<>();

    public static ArrayList<List<Index>> getAllRoutes() {
        return allRoutes;
    }

    @Override
    public void printAllPaths(int[][] primitiveMatrix, Index s, Index d) {
        try{
            if(primitiveMatrix.length == 0)
                throw new NullPointerException();
        }
        catch (NullPointerException err){
            throw err;
        }
        this.matrix = new Matrix(primitiveMatrix);
        //boolean[] isVisited = new boolean[v];
        boolean[][] visited = new boolean[primitiveMatrix.length][primitiveMatrix.length];
        ArrayList<Index> pathList = new ArrayList<>();

        // add source to path[]
        pathList.add(s);

        // Call recursive utility
        printAllPathsUtil(s, d, visited, pathList);
    }

    // A recursive function to print
    // all paths from 'u' to 'd'.
    // isVisited[] keeps track of
    // vertices in current path.
    // localPathList<> stores actual
    // vertices in the current path

    @Override
    public void printAllPathsUtil(Index u, Index d,
                                   boolean[][] visited,
                                   List<Index> localPathList)
    {
        if (u.equals(d)) {
            allRoutes.add(new ArrayList<>(localPathList));
            // if match found then no need to traverse more till depth
            return;
        }
        // Mark the current node
        visited[u.row][u.column] = true;
        // Recur for all the vertices
        // adjacent to current vertex
        for (Index i : this.matrix.getReachables(u)) {
            if (!visited[i.row][i.column]) {
                // store current node
                localPathList.add(i);
                printAllPathsUtil(i, d, visited, localPathList);

                // remove current node
                localPathList.remove(i);
            }
        }
        // Mark the current node
        visited[u.row][u.column] = false;
    }

    // Find the shortest routes exists in out matrix
    @Override
    public ArrayList<List<Index>> GetTheShortestRoutes(ArrayList<List<Index>> AllRoutes, int[][] source){
        ArrayList<List<Index>> AllShortsRoutes = new ArrayList<>();
        int minPathSize= source.length * source.length;
        for(List<Index> path: AllRoutes){
            if(path.size() < minPathSize)
                minPathSize = path.size();
        }
        for(List<Index> pathlist: AllRoutes){
            if(pathlist.size() == minPathSize)
                AllShortsRoutes.add(pathlist);
        }
        return AllShortsRoutes;
    }

    // Function that checks if our input matrix out of boundi
    @Override
    public boolean ValidMatrix(int[][] Array) {
        try{
            if(Array.length >= 50)
                throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException err){
            throw  err;
        }
        return true;
    }
}
