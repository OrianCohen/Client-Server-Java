package src.algorithms;

import src.algorithms.interfaces.SubMarineAlgorithmInterface;

import java.util.*;
import java.util.stream.Collectors;

public class SubMarineAlgorithm implements SubMarineAlgorithmInterface {
    // ברגע שזה מלבן או מרובע מספר ה אינדקסים חייב להיות זוגי
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

    // נסדוק האם זה במצב מאונך או מאוזן
    public boolean isHorVar(Collection<Index> input) {
        final Iterator ite = input.iterator();
        Index firstIndex = (Index) ite.next();
        Index lastIndex = firstIndex;
        Index curr;
        while (ite.hasNext()) {
            lastIndex = (Index) ite.next();
        }

        if (firstIndex.mRow == lastIndex.mRow) {//מצב מאוזן
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
        if (firstIndex.mCol == lastIndex.mCol) {// מצב מאונך
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

    //נבדוק האם יש לאינדקס צמד (מטפלים במקרהשמספר האינסקסים ברכיב קשירות הוא זוגי)
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

    @Override
    public int subMarineGame(int[][] inputArray) {
        boolean flag = true;
        int count = 0;
        ArrayList<HashSet<Index>> allConnectedCom = new ConnectedComponent().findOneReachables(inputArray);
        for (HashSet<Index> obj : allConnectedCom) {
            //sort all the connected component
            Collection<Index> sortedDFS = obj.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
            if (sortedDFS.size() == 1) { //רק איבר אחד לא טוב
                continue;
            }
            if (isHorVar(sortedDFS)) { // האם זה רק במצב מאונך או מאוזן
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
