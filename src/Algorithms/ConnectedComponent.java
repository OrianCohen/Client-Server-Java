package src.Algorithms;

import src.BackEnd.Index;
import src.Models.ConnectedComponentInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectedComponent implements ConnectedComponentInterface {
    public Matrix matrix;

    @Override
    public ArrayList<HashSet<Index>> findOneReachables(int[][] Array) {
        this.matrix = new Matrix(Array);
        ArrayList<HashSet<Index>> connectedComponents = new ArrayList<>();
        for (int i = 0; i < Array.length; i++) {
            for (int j = 0; j < Array[i].length; j++) {
                if (Array[i][j] == 1) {
                    CopyOnWriteArrayList notFinalIndices = new CopyOnWriteArrayList();
                    HashSet<Index> finalIndices = new HashSet<>();
                    finalIndices.add(new Index(i, j));
                    matrix.primitiveMatrix[i][j] = -1;
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
                                    matrix.primitiveMatrix[((Index) suspect).row][((Index) suspect).column] = -1;
                                }
                                notFinalIndices.remove(k);
                            }
                        }
                    }
                    finalIndices.add(new Index(i, j));
                    connectedComponents.add(finalIndices);
                    for (Index k : finalIndices) {
                        Array[k.row][k.column] = -1;
                    }
                }
            }
        }
        return connectedComponents;
    }


}
