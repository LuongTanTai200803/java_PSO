package src;

import java.util.List;

public class PathFinder {
    private PathFindingAlgorithm algorithm;
    
    public PathFinder(PathFindingAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
    
    public List<int[]> solve(Maze maze, int[] start, int[] goal) {
        return algorithm.findPath(maze, start, goal);
    }
    
    public void printPath(List<int[]> path) {
        System.out.println("Path found: " + path.size() + " steps");
        for (int[] pos : path) {
            System.out.printf("(%d, %d) ", pos[0], pos[1]);
        }
        System.out.println();
    }
}