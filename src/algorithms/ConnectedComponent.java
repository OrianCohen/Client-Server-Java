package src.algorithms;

import src.algorithms.interfaces.ConnectedComponentInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;
/*
* This class help us to find all connected component from a given matrix from a client
* */

public class ConnectedComponent implements ConnectedComponentInterface {


    @Override
    public ArrayList<HashSet<Index>> findOneReachables(int[][] inputArray) {
        Matrix matrix = new Matrix(inputArray);
        ArrayList<HashSet<Index>> connectedComponents = new ArrayList<>();

        //we will run along all the entire matrix if we will find an Index with value 1 we will check all the connected component.. each index we pass thorough we will sign with (-1)
        for (int i = 0; i < inputArray.length; i++) {
            for (int j = 0; j < inputArray[i].length; j++) {
                if (inputArray[i][j] == 1) {
                    CopyOnWriteArrayList<Index> notFinalIndices = new CopyOnWriteArrayList();
                    HashSet<Index> finalIndices = new HashSet<>();
                    finalIndices.add(new Index(i, j));
                    matrix.mPrimitiveMatrix[i][j] = -1;
                    // we will check the root index and we will find all the neighbor and cross matrix that have value 1
                    if (matrix.getReachables(new Index(i, j)) != null) {
                        notFinalIndices.addAll(matrix.getReachables(new Index(i, j)));
                    }
                    while (!(notFinalIndices.isEmpty())) {
                        for (Object k : notFinalIndices) {
                            if (matrix.getReachables((Index) k) == null) {
                                notFinalIndices.remove(k);
                                finalIndices.add((Index) k);
                            } else {
                                notFinalIndices.addAll(matrix.getReachables((Index) k));
                                finalIndices.add((Index) k);
                                for (Object suspect : notFinalIndices) {
                                    matrix.mPrimitiveMatrix[((Index) suspect).mRow][((Index) suspect).mCol] = -1;
                                }
                                notFinalIndices.remove(k);
                            }
                        }
                    }
                    finalIndices.add(new Index(i, j));
                    connectedComponents.add(finalIndices);
                    for (Index k : finalIndices) {
                        inputArray[k.mRow][k.mCol] = -1;
                    }
                }
            }
        }
        return connectedComponents;
    }


}
