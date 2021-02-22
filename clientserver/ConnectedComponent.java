package clientserver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class ConnectedComponent implements ConnectedComponentInterface {
    public Matrix matrix;

    @Override
    public ArrayList<HashSet<Index>> findOneReachables(int[][] array) {
        this.matrix = new Matrix(array);
        ArrayList<HashSet<Index>> connectedComponents = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] == 1) {
//                    Map<Index,HashSet<Index>> notFinalIndices = new HashMap<>();
                    //Map<Index, > notFinalIndices = new HashMap<>();
                    //List<Index> notFinalIndices = new ArrayList<>();
                    CopyOnWriteArrayList notFinalIndices = new CopyOnWriteArrayList();
                    HashSet<Index> finalIndices = new HashSet<>();
//                    notFinalIndices.putAll(,getReachables(new Index(i,j)));
                    finalIndices.add(new Index(i, j));
                    matrix.primitiveMatrix[i][j] = -1;
                    if (matrix.getReachables(new Index(i, j)) != null) {
                        notFinalIndices.addAll(matrix.getReachables(new Index(i, j)));
                    }

                    //System.out.println(notFinalIndices);
                    while (!(notFinalIndices.isEmpty())) {
                        for (Object k : notFinalIndices) {
                            if (matrix.getReachables((Index) k) == null) {
                                notFinalIndices.remove((Index) k);
                                finalIndices.add((Index) k);

                            } else {
                                notFinalIndices.addAll(matrix.getReachables((Index) k));
                                finalIndices.add((Index) k);
                                for (Object suspect : notFinalIndices) {
                                    matrix.primitiveMatrix[((Index) suspect).row][((Index) suspect).column] = -1;
                                }
                                //this.primitiveMatrix[((Index) k).row][((Index) k).column] = -1;
                                notFinalIndices.remove((Index) k);
                            }
                            //notFinalIndices = new CopyOnWriteArrayList();
                        }
                    }

                    finalIndices.add(new Index(i, j));

//                    System.out.println("FROM FILTERED" + "i=" + i + "j=" + j);
//                    System.out.println(finalIndices);
                    connectedComponents.add(finalIndices);
                    for (Index k : finalIndices) {
                        array[k.row][k.column] = -1;
                    }
                }

            }
        }
        return connectedComponents;
    }


}
