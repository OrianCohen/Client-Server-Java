package src.Algorithms;

import java.util.*;
import java.util.stream.Collectors;

public class SubMarineAlgorithm implements SubMarineAlgorithmInterface {
    private ConnectedComponentInterface AllConnectedCom;

    // ברגע שזה מלבן או מרובע מספר ה אינדקסים חייב להיות זוגי
    public boolean isRectangle(Collection<Index> input){
        final Iterator ite = input.iterator();
        Index CurrIndex = (Index) ite.next();
        Index nextIndex = null;

        while (ite.hasNext()) {
            nextIndex= (Index) ite.next();
            if(CurrIndex.row > nextIndex.row )
                return false;
            CurrIndex =nextIndex;
        }
        return true;
    }

    public Index getLatIndex(Collection<Index> input)
    {
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
        Index curr = null;
        while (ite.hasNext()) {
            lastIndex = (Index) ite.next();
        }

        if (firstIndex.row == lastIndex.row) {//מצב מאוזן
            Iterator<Index> itera = input.iterator();
            curr = (Index) itera.next();
            while (itera.hasNext()) {
                if (curr.row != lastIndex.row)
                    return false;
                curr = (Index) itera.next();
            }
            return true;
        }
        if(firstIndex.column == lastIndex.column) {// מצב מאונך
            Iterator<Index> itera = input.iterator();
            curr = (Index) itera.next();
            while (itera.hasNext()) {
                if (curr.column != lastIndex.column)
                    return false;
                curr = (Index) itera.next();
            }
            return true;
        }
        return false;
}

    //נבדוק האם יש לאינדקס צמד (מטפלים במקרהשמספר האינסקסים ברכיב קשירות הוא זוגי)
    public boolean isPairs(Collection<Index> input){
        final Iterator ite = input.iterator();
        Index firstIndex = (Index) ite.next();
        Index curr = null;
//        int i=0;
//        int sizeCol = getLatIndex(input).column;
        while (ite.hasNext()) {
            if(input.contains(new Index(firstIndex.row, firstIndex.column +1)))
                firstIndex = (Index) ite.next();
            else return false;
            if(ite.hasNext()) {
                curr = (Index) ite.next();
                firstIndex = curr;
            }
            else continue;
//
//            i++;
        }
        return true;
    }

    @Override
    public int subMarineGame(int[][] Array) {
        AllConnectedCom = new ConnectedComponent();
        boolean flag = true;
        int count =0;
        ArrayList<HashSet<Index>> allConnectedCom = AllConnectedCom.findOneReachables(Array);
        for (HashSet<Index> obj : allConnectedCom) {
            //sort all the connected component
            Collection<Index> sortedDFS = obj.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
            System.out.println(sortedDFS);


            if (sortedDFS.size() == 1) { //רק איבר אחד לא טוב
                flag = false;
                continue;
            }
            if (isHorVar(sortedDFS)) { // האם זה רק במצב מאונך או מאוזן
                count++;
                continue;
            }
            Index last = getLatIndex(sortedDFS);
            Index first = sortedDFS.iterator().next();
            if((last.column - first.column) %2 !=0)
                if(!isPairs(sortedDFS))
                    continue;

            if (isRectangle(sortedDFS)) {
                count++;
                continue;
            }
        }
        return count;
    }


//    public static void main(String[] args) {
////        int[][] source = {
////                {1, 1, 0,1,1},
////                {0, 0, 0,1,1},
////                {1, 1, 1,1,1}
////
////        };
////        SubMarineAlgorithm sub = new SubMarineAlgorithm();
////        int count ;
////        count = sub.subMarineGame(source);
////        System.out.println("test orian task three " + count);
////    }
//
}
