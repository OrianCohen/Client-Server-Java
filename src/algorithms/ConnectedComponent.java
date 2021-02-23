package src.algorithms;

import src.algorithms.interfaces.ConnectedComponentInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectedComponent<T> implements ConnectedComponentInterface {

    @Override
    public ArrayList<HashSet<Index>> findOneReachables(int[][] inputArray) {
        Matrix matrix = new Matrix(inputArray);
        ArrayList<HashSet<Index>> connectedComponents = new ArrayList<>();
        for (int i = 0; i < inputArray.length; i++) {
            for (int j = 0; j < inputArray[i].length; j++) {
                if (inputArray[i][j] == 1) {
                    CopyOnWriteArrayList<Index> notFinalIndices = new CopyOnWriteArrayList();
                    HashSet<Index> finalIndices = new HashSet<>();
                    finalIndices.add(new Index(i, j));
                    matrix.mPrimitiveMatrix[i][j] = -1;
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
