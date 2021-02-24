package src.algorithms;

import src.algorithms.interfaces.SubMarineAlgorithmInterface;

import java.util.*;
import java.util.stream.Collectors;

public class SubMarineAlgorithm implements SubMarineAlgorithmInterface {

    // we will check if the connected com is square or rectangle
    public boolean isRectangle(Collection<Index> input) {
        final Iterator ite = input.iterator();
        Index CurrIndex = (Index) ite.next();
        Index nextIndex;

        while (ite.hasNext()) {
            nextIndex = (Index) ite.next();
            if (CurrIndex.mRow > nextIndex.mRow) {
                return false;
            }
            CurrIndex = nextIndex;
        }
        return true;
    }


    public Index getLatIndex(Collection<Index> input) {
        final Iterator ite = input.iterator();
        Index lastIndex = (Index) ite.next();
        while (ite.hasNext()) {
            lastIndex = (Index) ite.next();
        }
        return lastIndex;
    }

    // This function help us check if the connected com in horizon r vertical position
    public boolean isHorVar(Collection<Index> input) {
//        final Iterator ite = input.iterator();
//        Index firstIndex = (Index) ite.next();
//        Index lastIndex = firstIndex;
//        Index curr;
//        while (ite.hasNext()) {
//            lastIndex = (Index) ite.next();
//        }
        Index lastIndex = getLatIndex(input);
        Index firstIndex = input.iterator().next();
        Index curr;

        if (firstIndex.mRow == lastIndex.mRow) { // In case of horizon position
            Iterator<Index> itera = input.iterator();
            curr = itera.next();
            while (itera.hasNext()) {
                if (curr.mRow != lastIndex.mRow) {
                    return false;
                }
                curr = itera.next();
            }
            return true;
        }
        if (firstIndex.mCol == lastIndex.mCol) { // in case of vertical position
            Iterator<Index> itera = input.iterator();
            curr = itera.next();
            while (itera.hasNext()) {
                if (curr.mCol != lastIndex.mCol) {
                    return false;
                }
                curr = itera.next();
            }
            return true;
        }
        return false;
    }

    // We are checking the case the number of indexes is even, checking pairs
    public boolean isPairs(Collection<Index> input) {
        final Iterator ite = input.iterator();
        Index firstIndex = (Index) ite.next();
        Index curr;
        while (ite.hasNext()) {
            if (input.contains(new Index(firstIndex.mRow, firstIndex.mCol + 1))) {
                firstIndex = (Index) ite.next();
            } else {
                return false;
            }
            if (ite.hasNext()) {
                curr = (Index) ite.next();
                firstIndex = curr;
            }
        }
        return true;
    }

    //
    @Override
    public int subMarineGame(int[][] inputArray) {
        boolean flag = true;
        int count = 0;
        //we will use the function from task one to help us get all the connected component, and then we will run in for loop on each connected com
        ArrayList<HashSet<Index>> allConnectedCom = new ConnectedComponent().findOneReachables(inputArray);
        for (HashSet<Index> obj : allConnectedCom) {
            //sort all the connected component (from low index to high index // using functional interfaces
            // advantage : we dont need to create another data structure
            Collection<Index> sortedDFS = obj.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));

            if (sortedDFS.size() == 1) { // In case only one Index is appear, we will not count it
                continue;
            }
            if (isHorVar(sortedDFS)) { // we will check if this connected component is in horizon position or vertical
                count++;
                continue;
            }
            Index last = getLatIndex(sortedDFS);
            Index first = sortedDFS.iterator().next();
            if ((last.mCol - first.mCol) % 2 != 0)
                if (!isPairs(sortedDFS))
                    continue;

            if (isRectangle(sortedDFS)) {
                count++;
                continue;
            }
        }
        return count;
    }

}
