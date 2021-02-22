package clientserver;

import java.util.List;

public interface TheShortestRoutesInterface {
    void printAllPaths(int[][] primitiveMatrix, Index start, Index end);
    void printAllPathsUtil(Index u, Index d, boolean[][] visited,List<Index> localPathList);
}
