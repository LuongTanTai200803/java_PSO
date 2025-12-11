package src;

import src.utils.Constants;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Maze maze = new Maze();
        maze.printMaze();

        int[] start = new int[]{Constants.START_ROW, Constants.START_COL};
        int[] goal  = new int[]{Constants.GOAL_ROW,  Constants.GOAL_COL};

        // Chỉ dùng PSO
        PathFinder pathFinder = new PathFinder(new PSO());
        List<int[]> path = pathFinder.solve(maze, start, goal);
        pathFinder.printPath(path);
    }
}