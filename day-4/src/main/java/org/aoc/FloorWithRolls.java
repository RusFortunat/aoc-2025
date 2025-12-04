package org.aoc;


import org.aoc.graphics.SnapshotMaker;

public class FloorWithRolls {

    private static int ITERATIONS = 0;

    public static int countAccessibleRolls(int[][] floorWithRolls) {

        int countRolls = 0;

        int removedRolls = -1;
        int iteration = 0;
        while(removedRolls != 0) {

            int removedRollsPerIteration = 0 ;

            for (int x = 0; x < floorWithRolls.length; x++) {
                int prevX = x == 0 ? -1 : x - 1;
                int nextX = x == floorWithRolls.length - 1 ? -1 : x + 1;
                for (int y = 0; y < floorWithRolls[x].length; y++) {
                    int prevY = y == 0 ? -1 : y - 1;
                    int nextY = y == floorWithRolls.length - 1 ? -1 : y + 1;

                    int adjacentRollsCount = 0;
                    // left
                    if (prevX != -1) {
                        // top left
                        if (prevY != -1) {
                            adjacentRollsCount += floorWithRolls[prevX][prevY];
                        }
                        // middle left
                        adjacentRollsCount += floorWithRolls[prevX][y];
                        // bottom left
                        if (nextY != -1) {
                            adjacentRollsCount += floorWithRolls[prevX][nextY];
                        }
                    }
                    // middle top
                    if (prevY != -1) {
                        adjacentRollsCount += floorWithRolls[x][prevY];
                    }
                    // middle bottom
                    if (nextY != -1) {
                        adjacentRollsCount += floorWithRolls[x][nextY];
                    }
                    // right
                    if (nextX != -1) {
                        // top right
                        if (prevY != -1) {
                            adjacentRollsCount += floorWithRolls[nextX][prevY];
                        }
                        // middle right
                        adjacentRollsCount += floorWithRolls[nextX][y];
                        // bottom right
                        if (nextY != -1) {
                            adjacentRollsCount += floorWithRolls[nextX][nextY];
                        }
                    }

                    if (adjacentRollsCount < 4 && floorWithRolls[x][y] == 1) {
                        floorWithRolls[x][y] = 0;
                        removedRollsPerIteration++;
                    }
                }
            }

            countRolls += removedRollsPerIteration;
            removedRolls = removedRollsPerIteration;

            // create a snapshot
            SnapshotMaker.createSnapshot(floorWithRolls, iteration);
            iteration++;
        }

        ITERATIONS = iteration;

        return countRolls;
    }

    public static int getIterations() {
        return ITERATIONS;
    }
}
