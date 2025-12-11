package src;

import java.util.*;
import src.utils.Constants;

public class PSO implements PathFindingAlgorithm {

    private final Random rnd = new Random();

    @Override
    public List<int[]> findPath(Maze maze, int[] start, int[] goal) {
        int dim = Constants.MAZE_SIZE * 2; // length of action sequence
        // positions and velocities
        double[][] pos = new double[Constants.population][dim];
        double[][] vel = new double[Constants.population][dim];

        double[][] pBestPos = new double[Constants.population][dim];
        double[] pBestFitness = new double[Constants.population];

        Arrays.fill(pBestFitness, Double.NEGATIVE_INFINITY);

        double[] gBestPos = new double[dim];
        double gBestFitness = Double.NEGATIVE_INFINITY;

        // init
        for (int i = 0; i < Constants.population; i++) {
            for (int d = 0; d < dim; d++) {
                pos[i][d] = rnd.nextInt(4); // 0=Up, 1=Down, 2=Left, 3=Right
                vel[i][d] = rnd.nextDouble() * 2 - 1;
                pBestPos[i][d] = pos[i][d];
            }
            int[] actions = discretize(pos[i]);
            double fit = maze.simulatePath(actions, start, goal);
            pBestFitness[i] = fit;
            if (fit > gBestFitness) {
                gBestFitness = fit;
                System.arraycopy(pBestPos[i], 0, gBestPos, 0, dim);
            }
        }
       
        // velocity update rule:
        // vel[i][d] = w * vel[i][d] + c1 * r1 * (pBestPos[i][d] - pos[i][d]) + c2 * r2 * (gBestPos[d] - pos[i][d]);
    
        /* example:
        Iteration khởi tạo particle 0:
        d=0:  pos[0][0] = rnd.nextInt(4) = 2    // action: Left (2)
            vel[0][0] = rnd.nextDouble() * 2 - 1 = 0.45 * 2 - 1 = -0.1
            pBestPos[0][0] = 2

        Update location pos[0][0]:
        vel[0][0] = 0.72 * (-0.1) + 1.49 * 0.34 * (2 - 2) + 1.49 * 0.67 * (2.5 - 2)
                = -0.072 + 0 + 0.746
                = 0.674

        pos[0][0] += vel[0][0]
        pos[0][0] = 2 + 0.674 = 2.674  // clamp to [0, 3] → 2.674

        discretize(2.674) = round(2.674) = 3  // action change to 2 → 3 (Right)
        */
        // iterations
        for (int it = 0; it < Constants.iterations; it++) {
            for (int i = 0; i < Constants.population; i++) {
                for (int d = 0; d < dim; d++) {
                    double r1 = rnd.nextDouble();
                    double r2 = rnd.nextDouble();
                    vel[i][d] = Constants.W * vel[i][d]

                                + Constants.C1 * r1 * (pBestPos[i][d] - pos[i][d])
                                + Constants.C2 * r2 * (gBestPos[d] - pos[i][d]);
                    pos[i][d] += vel[i][d];
                    
                    // scope [0,3]
                    if (pos[i][d] < 0) pos[i][d] = 0;
                    if (pos[i][d] > 3) pos[i][d] = 3;
                }
                // test actions = 3
                int[] actions = discretize(pos[i]);
                
                // test: maze.simulatePath(3, {0,0}, {9,9})
                double fit = maze.simulatePath(actions, start, goal);

                if (fit > pBestFitness[i]) {
                    pBestFitness[i] = fit;
                    System.arraycopy(pos[i], 0, pBestPos[i], 0, dim);
                }
                if (fit > gBestFitness) {
                    gBestFitness = fit;
                    System.arraycopy(pos[i], 0, gBestPos, 0, dim);
                }
            }
            // Optional: early stop if reached goal with very high fitness
            if (gBestFitness > 900) break;
        }

        int[] bestActions = discretize(gBestPos);
        return maze.pathFromActions(bestActions, start, goal);
    }

    @Override
    public String getName() {
        return "PSO (Particle Swarm Optimization)";
    }

    private int[] discretize(double[] vector) {
        int[] actions = new int[vector.length];
        for (int i = 0; i < vector.length; i++) {

            int a = (int)Math.round(vector[i]);
            if (a < 0) a = 0;
            if (a > 3) a = 3;
            actions[i] = a;
        }
        return actions;
    }
}