package src.algorithms;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/*
 * A matrix represents a collection of cells in a 2D array, known as its indices
 * Represents the common functionality required for all matrices
 * */

public class Matrix implements Serializable {
    int[][] mPrimitiveMatrix;

    public Matrix(int[][] oArray) {
        mPrimitiveMatrix = Arrays
                .stream(oArray)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }

    // Function that return A collection of index's (neighbors and cross matrix)
    public Collection<Index> getAdjacentIndices(final Index index) {
        Collection<Index> list = new ArrayList<>();
        int extracted = -1;
        try {
            extracted = mPrimitiveMatrix[index.mRow + 1][index.mCol];
            list.add(new Index(index.mRow + 1, index.mCol));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            extracted = mPrimitiveMatrix[index.mRow][index.mCol + 1];
            list.add(new Index(index.mRow, index.mCol + 1));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            extracted = mPrimitiveMatrix[index.mRow - 1][index.mCol];
            list.add(new Index(index.mRow - 1, index.mCol));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            extracted = mPrimitiveMatrix[index.mRow][index.mCol - 1];
            list.add(new Index(index.mRow, index.mCol - 1));
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
        try {
            extracted = mPrimitiveMatrix[index.mRow - 1][index.mCol - 1];
            list.add(new Index(index.mRow - 1, index.mCol - 1));
        } catch (ArrayIndexOutOfBoundsException outOfBounds) {
        }

        try {
            extracted = mPrimitiveMatrix[index.mRow + 1][index.mCol + 1];
            list.add(new Index(index.mRow + 1, index.mCol + 1));
        } catch (ArrayIndexOutOfBoundsException outOfBounds) {
        }

        try {
            extracted = mPrimitiveMatrix[index.mRow + 1][index.mCol - 1];
            list.add(new Index(index.mRow + 1, index.mCol - 1));
        } catch (ArrayIndexOutOfBoundsException outOfBounds) {
        }

        try {
            extracted = mPrimitiveMatrix[index.mRow - 1][index.mCol + 1];
            list.add(new Index(index.mRow - 1, index.mCol + 1));
        } catch (ArrayIndexOutOfBoundsException outOfBounds) {
        }


        return list;
    }

    public int getValue(Index index) {
        return mPrimitiveMatrix[index.mRow][index.mCol];
    }

    // Get all neighbor that value 1 using stream
    public Collection<Index> getReachables(Index index) {
        ArrayList<Index> filteredIndices = new ArrayList<>();
        this.getAdjacentIndices(index).stream().filter(i -> getValue(i) == 1)
                .map(neighbor -> filteredIndices.add(neighbor)).collect(Collectors.toList());
        if (filteredIndices.isEmpty()) {
            return null;
        }
        return filteredIndices;
    }

    public void printMatrix() {
        for (int[] row : mPrimitiveMatrix) {
            String s = Arrays.toString(row);
            System.out.println(s);
        }
    }

    public final int[][] getPrimitiveMatrix() {
        return mPrimitiveMatrix;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int[] row : mPrimitiveMatrix) {
            stringBuilder.append(Arrays.toString(row));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}