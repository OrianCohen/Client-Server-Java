package src.Models;

import src.BackEnd.Index;

import java.util.ArrayList;
import java.util.HashSet;

public interface ConnectedComponentInterface {
    ArrayList<HashSet<Index>> findOneReachables(int[][] Array);


}