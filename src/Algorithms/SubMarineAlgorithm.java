package src.Algorithms;

import src.BackEnd.Index;
import src.Models.ConnectedComponentInterface;
import src.Models.SubMarineAlgorithmInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class SubMarineAlgorithm implements SubMarineAlgorithmInterface {
    private ConnectedComponentInterface AllConnectedCom;


    @Override
    public int subMarineGame(int[][] Array) {
        AllConnectedCom = new ConnectedComponent();
        boolean flag = true;
        ArrayList<HashSet<Index>> allConnectedCom = AllConnectedCom.findOneReachables(Array);
        for (HashSet<Index> obj : allConnectedCom) {
            //sort all the connected component
            Collection<Index> sortedDFS = obj.stream().sorted().collect(Collectors.toCollection(LinkedHashSet::new));
            System.out.println(sortedDFS);

//            //find first and last index
//            final Iterator ite = sortedDFS.iterator();
//            Index lastIndex = (Index) ite.next();
//            Index firstIndex = lastIndex;
//            while (ite.hasNext()) {
//                lastIndex = (Index) ite.next();
//            }

            if(flag) {
                int countVertical = 1;
                int countHorizan = 1;
//                int i = 1;
//                    while (i < Array[0].length && i < Array.length) {
                        for (Index elem : sortedDFS) {

                            if(elem.row + 1 < Array[elem.column].length)
                                if (Array[elem.row + 1][elem.column] == -1)
                                    countVertical++;

                            if(elem.column + 1 < Array[elem.column].length)
                                if (Array[elem.row][elem.column + 1] == -1)
                                    countHorizan++;
//                        }
//                    i++;
                }

                if(countHorizan == 1 || countVertical ==1)
                    flag=false;
                if (countHorizan == sortedDFS.size() || countVertical == sortedDFS.size()) {
                    flag = true;
                }
                if (countHorizan == countVertical) {
                    flag = true;
                }
                if (countVertical > sortedDFS.size())
                    flag = true;

                if (countHorizan > sortedDFS.size())
                    flag = true;

            }

        }
        if (flag)
            return allConnectedCom.size();
        return -1;
    }

    public static void main(String[] args) {
        int[][] source = {
                {0, 0, 0,1,0},
                {0, 0, 0,1,0},
                {0, 0, 0,1,0}

        };
        SubMarineAlgorithm sub = new SubMarineAlgorithm();
        int count ;
        count = sub.subMarineGame(source);
        System.out.println("test orian task three" + count);
    }

}
