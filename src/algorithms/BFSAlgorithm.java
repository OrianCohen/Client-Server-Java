package src.algorithms;

import src.algorithms.interfaces.TheShortestRoutesInterface;
import src.exception.InvalidInputException;
import java.util.ArrayList;
import java.util.List;
/*
* This class help us to solve task two and four, in a given of matrix we will fins all the possible routes that exists
* */

public class BFSAlgorithm implements TheShortestRoutesInterface {
    public Matrix mMatrix;
    public ArrayList<List<Index>> mAllRoutes = new ArrayList<>();

    public ArrayList<List<Index>> getAllRoutes() {
        return mAllRoutes;
    }

    // Root function for task 2 & 4, from here we will check all the possible routes from 's' to d' index, in the end we will get all the possible routes to our data member mAllRoutes
    @Override
    public void checkAllPossibleRoutes(int[][] primitiveMatrix, Index s, Index d) throws InvalidInputException {
        if (primitiveMatrix.length == 0) {
            throw new InvalidInputException();
        }
        mMatrix = new Matrix(primitiveMatrix);
        //boolean[] isVisited = new boolean[v];
        boolean[][] visited = new boolean[primitiveMatrix.length][primitiveMatrix.length];
        ArrayList<Index> pathList = new ArrayList<>();
        // add source to path[]
        pathList.add(s);
        // Call recursive utility
        checkAllPossibleRoutesRecursive(s, d, visited, pathList);
    }

    // A recursive function that find all the paths from start index to end index
    @Override
    public void checkAllPossibleRoutesRecursive(Index u, Index d,
                                                boolean[][] visited,
                                                List<Index> localPathList) {
        if (u.equals(d)) {
            mAllRoutes.add(new ArrayList<>(localPathList));
            return;
        }
        // Mark the current node
        visited[u.mRow][u.mCol] = true;
        // Recur for all the vertices
        // adjacent to current vertex
        for (Index i : this.mMatrix.getReachables(u)) {
            if (!visited[i.mRow][i.mCol]) {
                // store current node
                localPathList.add(i);
                checkAllPossibleRoutesRecursive(i, d, visited, localPathList);
                localPathList.remove(i);
            }
        }
        // We will mark the current node
        visited[u.mRow][u.mCol] = false;
    }

    // Find the shortest routes from all the possible routes exists in our matrix in a given start index and last index
    @Override
    public ArrayList<List<Index>> GetTheShortestRoutes(ArrayList<List<Index>> AllRoutes, int[][] source) {
        ArrayList<List<Index>> AllShortsRoutes = new ArrayList<>();
        int minPathSize = source.length * source.length;
        for (List<Index> path : AllRoutes) {
            if (path.size() < minPathSize)
                minPathSize = path.size();
        }
        for (List<Index> pathlist : AllRoutes) {
            if (pathlist.size() == minPathSize)
                AllShortsRoutes.add(pathlist);
        }
        return AllShortsRoutes;
    }

//     Function that checks if our input matrix out of bound greater then 50
        @Override
        public boolean ValidMatrix(int[][] array) throws InvalidInputException {
            if (array.length >= 50) {
                throw new InvalidInputException();
            }
            return true;
        }
}
