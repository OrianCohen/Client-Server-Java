package src.algorithms;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class Index implements Serializable, Comparable<Index> {
    public int mRow, mCol;

    // Constructor
    public Index(int oRow, int oColumn) {
        this.mRow = oRow;
        this.mCol = oColumn;
    }

    @Override
    public String toString() {
        return "(" + mRow + "," + mCol + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Index index = (Index) o;
        return mRow == index.mRow &&
                mCol == index.mCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mRow, mCol);
    }

    public static void main(String[] args) {
        Index myIndex = new Index(2, 2);
        System.out.println(myIndex);
    }


    @Override
    public int compareTo(@NotNull Index o) {
        return toString().compareTo(o.toString());
    }

}