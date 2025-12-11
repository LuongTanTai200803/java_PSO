package src;

import java.util.List;

public interface PathFindingAlgorithm {
    List<int[]> findPath(Maze maze, int[] start, int[] goal);
    String getName();
}