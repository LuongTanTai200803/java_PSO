package src.utils;

public final class Constants {

    // Kích thước Mê cung
    public static final int MAZE_SIZE = 10; 
    public static final int WALL = 1;
    public static final int PATH = 0;

    
    public static final int START_ROW = 0;
    public static final int START_COL = 0;
    public static final int GOAL_ROW = MAZE_SIZE - 1;
    public static final int GOAL_COL = MAZE_SIZE - 1;

    // Tham số Thuật toán PSO (Sẽ dùng ở Phần 2)
    public static final int population = 20;
    public static final int iterations = 100;
    public static final int PATH_LENGTH = 50;
    
    // Hằng số PSO
    public static final double W = 0.72; // inertia weight
    public static final double C1 = 1.49; // person good cognitive learning factor
    public static final double C2 = 1.49; // good social learning factor
    // public static final double MAX_VELOCITY = 4.0; 
    // public static final double MIN_VELOCITY = -4.0;
    public static final double MIN_WEIGHT = 0.4; // Trọng số quán tính tối thiểu
    public static final double MAX_WEIGHT = 0.9; // Trọng số quán tính tối đa

    // Tham số Đánh giá Fitness
    // Các giá trị này quyết định chất lượng của đường đi
    public static final double PENALTY_WALL = 1000.0; // Phạt khi đâm vào tường/ra biên (giá trị lớn)
    public static final double PENALTY_LENGTH_FACTOR = 0.1; // Phạt nhỏ cho mỗi bước di chuyển
    public static final double REWARD_GOAL_FACTOR = 100.0; // Thưởng khi đạt/gần Goal
}