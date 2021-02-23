package src.algorithms.interfaces;

import src.algorithms.Index;

import java.util.ArrayList;
import java.util.HashSet;

public interface ConnectedComponentInterface {
    ArrayList<HashSet<Index>> findOneReachables(int[][] Array);


}
