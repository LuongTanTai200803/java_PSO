// ...existing code...
package src;

import src.utils.Constants;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class Maze {

    private int[][] map; // Bản đồ mê cung

    public Maze() {
        this.map = new int[Constants.MAZE_SIZE][Constants.MAZE_SIZE];
        initializeMazeMap();
    }

    // Khởi tạo bản đồ mê cung (Ví dụ: tạo một mê cung đơn giản)
    private void initializeMazeMap() {
        // Khởi tạo tất cả là đường đi (0)
        for (int i = 0; i < Constants.MAZE_SIZE; i++) {
            for (int j = 0; j < Constants.MAZE_SIZE; j++) {
                map[i][j] = Constants.PATH;
            }
        }
        
        // Thêm một số tường đơn giản cho mục đích thử nghiệm (1)
        // Ví dụ: một bức tường ở giữa
        for (int i = 2; i < Constants.MAZE_SIZE - 2; i++) {
            map[i][Constants.MAZE_SIZE / 2] = Constants.WALL;
        }
        
        // Đảm bảo Start và Goal không bị tường chặn ngay lập tức (nếu cần)
        map[Constants.START_ROW][Constants.START_COL] = Constants.PATH;
        map[Constants.GOAL_ROW][Constants.GOAL_COL] = Constants.PATH;
    }
  
    // Phương thức in mê cung để kiểm tra
    public void printMaze() {
        System.out.println("--- MAZE MAP (0=Path, 1=Wall) ---");
        for (int i = 0; i < Constants.MAZE_SIZE; i++) {
            for (int j = 0; j < Constants.MAZE_SIZE; j++) {
                if (i == Constants.START_ROW && j == Constants.START_COL) {
                    System.out.print(" S "); // Start
                } else if (i == Constants.GOAL_ROW && j == Constants.GOAL_COL) {
                    System.out.print(" G "); // Goal
                } else {
                    System.out.printf(" %d ", map[i][j]);
                }
            }
            System.out.println();
        }
    }

    // Simulate a sequence of actions and return a fitness score (higher is better)
    // actions: 0=Up,1=Down,2=Left,3=Right
    public double simulatePath(int[] actions, int[] start, int[] goal) {
        int r = start[0], c = start[1];
        int steps = 0;
        int wallHits = 0;
        for (int a : actions) {
            steps++;
            int nr = r, nc = c;
            if (a == 0) nr = r - 1;
            else if (a == 1) nr = r + 1;
            else if (a == 2) nc = c - 1;
            else if (a == 3) nc = c + 1;

            // check bounds
            if (nr < 0 || nr >= Constants.MAZE_SIZE || nc < 0 || nc >= Constants.MAZE_SIZE) {
                wallHits++;
                continue;
            }

            if (map[nr][nc] == Constants.WALL) {
                wallHits++;
                continue;
            }

            r = nr; c = nc;

            // reached goal
            if (r == goal[0] && c == goal[1]) {
                // reward reaching goal, shorter steps better
                return 1000.0 - steps - wallHits * 50;
            }
        }

        // not reached goal: negative score based on manhattan distance, steps and wallHits
        int manhattan = Math.abs(goal[0] - r) + Math.abs(goal[1] - c);
        return - (manhattan * 5.0 + steps * 0.5 + wallHits * 20.0);
    }

    // From an action sequence, return visited coordinates up to reaching goal (or full sequence)
    public List<int[]> pathFromActions(int[] actions, int[] start, int[] goal) {
        List<int[]> path = new ArrayList<>();
        int r = start[0], c = start[1];
        path.add(new int[]{r, c});
        for (int a : actions) {
            int nr = r, nc = c;
            if (a == 0) nr = r - 1;
            else if (a == 1) nr = r + 1;
            else if (a == 2) nc = c - 1;
            else if (a == 3) nc = c + 1;

            if (nr < 0 || nr >= Constants.MAZE_SIZE || nc < 0 || nc >= Constants.MAZE_SIZE) {
                // invalid -> ignore move (stay)
                continue;
            }
            if (map[nr][nc] == Constants.WALL) {
                // blocked -> ignore move
                continue;
            }
            r = nr; c = nc;
            path.add(new int[]{r, c});
            if (r == goal[0] && c == goal[1]) break;
        }
        return path;
    }
}
