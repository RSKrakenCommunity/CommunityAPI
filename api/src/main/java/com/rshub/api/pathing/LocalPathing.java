// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright © 2021 Trenton Kress
//  This file is part of project: FreeDev
//
package com.rshub.api.pathing;

import com.rshub.api.map.ClipFlag;
import com.rshub.api.map.Region;
import com.rshub.api.pathing.strategy.FixedTileStrategy;
import com.rshub.definitions.maps.WorldTile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LocalPathing {
    private static final int GRAPH_SIZE = 128;
    private static final int QUEUE_SIZE = (GRAPH_SIZE * GRAPH_SIZE) / 4;
    private static final int ALTERNATIVE_ROUTE_MAX_DISTANCE = 100;
    private static final int ALTERNATIVE_ROUTE_RANGE = 10;

    private static final int DIR_NORTH = 0x1;
    private static final int DIR_EAST = 0x2;
    private static final int DIR_SOUTH = 0x4;
    private static final int DIR_WEST = 0x8;

    private static final int[][] directions = new int[GRAPH_SIZE][GRAPH_SIZE];
    private static final int[][] distances = new int[GRAPH_SIZE][GRAPH_SIZE];
    private static final int[][] clip = new int[GRAPH_SIZE][GRAPH_SIZE];
    private static final int[] bufferX = new int[QUEUE_SIZE];
    private static final int[] bufferY = new int[QUEUE_SIZE];
    private static int exitX = -1;
    private static int exitY = -1;
    private static boolean isAlternative;

    public static int getLocalStepsTo(WorldTile start, int srcSize, RouteStrategy strategy, boolean findAlternative) {
        List<WorldTile> steps = findLocalRoute(start, srcSize, strategy, findAlternative);
        return steps == null ? -1 : steps.size();
    }

    public static boolean isReachable(WorldTile dest) {
        return getLocalStepsTo(dest, 1, new FixedTileStrategy(dest), false) > -1;
    }

    public static List<WorldTile> findLocalRoute(WorldTile start, int srcSize, RouteStrategy strategy, boolean findAlternative) {
        isAlternative = false;
        for (int x = 0; x < directions.length; x++) {
            for (int y = 0; y < directions.length; y++) {
                directions[x][y] = 0;
                distances[x][y] = 99999999;
            }
        }

        transmitClipData(start.getX(), start.getY(), start.getZ());
        boolean found = false;
        switch (srcSize) {
            case 1:
                found = checkSingleTraversal(start.getX(), start.getY(), strategy);
                break;
            case 2:
                found = checkDoubleTraversal(start.getX(), start.getY(), strategy);
                break;
            default:
                found = checkVariableTraversal(start.getX(), start.getY(), srcSize, strategy);
                break;
        }

        if (!found && !findAlternative) {
            System.out.println("Could not find route.");
            return null;
        }

        int graphBaseX = start.getX() - (GRAPH_SIZE / 2);
        int graphBaseY = start.getY() - (GRAPH_SIZE / 2);
        int endX = exitX;
        int endY = exitY;

        if (!found) {
            isAlternative = true;
            int lowestCost = Integer.MAX_VALUE;
            int lowestDistance = Integer.MAX_VALUE;

            int approxDestX = strategy.getApproxDestinationX();
            int approxDestY = strategy.getApproxDestinationY();

            for (int checkX = (approxDestX - ALTERNATIVE_ROUTE_RANGE); checkX <= (approxDestX + ALTERNATIVE_ROUTE_RANGE); checkX++) {
                for (int checkY = (approxDestY - ALTERNATIVE_ROUTE_RANGE); checkY <= (approxDestY + ALTERNATIVE_ROUTE_RANGE); checkY++) {
                    int graphX = checkX - graphBaseX;
                    int graphY = checkY - graphBaseY;
                    if (graphX < 0 || graphY < 0 || graphX >= GRAPH_SIZE || graphY >= GRAPH_SIZE || distances[graphX][graphY] >= ALTERNATIVE_ROUTE_MAX_DISTANCE)
                        continue;
                    int deltaX = 0;
                    int deltaY = 0;
                    if (approxDestX <= checkX) {
                        deltaX = 1 - approxDestX - (strategy.getApproxDestinationSizeX() - checkX);
                    } else
                        deltaX = approxDestX - checkX;
                    if (approxDestY <= checkY) {
                        deltaY = 1 - approxDestY - (strategy.getApproxDestinationSizeY() - checkY);
                    } else
                        deltaY = approxDestY - checkY;

                    int cost = (deltaX * deltaX) + (deltaY * deltaY);
                    if (cost < lowestCost || (cost <= lowestCost && distances[graphX][graphY] < lowestDistance)) {
                        lowestCost = cost;
                        lowestDistance = distances[graphX][graphY];
                        endX = checkX;
                        endY = checkY;
                    }
                }
            }

            if (lowestCost == Integer.MAX_VALUE || lowestDistance == Integer.MAX_VALUE)
                return null;
        }

        if (endX == start.getX() && endY == start.getY())
            return new ArrayList<>();

        int steps = 0;
        int traceX = endX;
        int traceY = endY;
        int direction = directions[traceX - graphBaseX][traceY - graphBaseY];
        int lastwritten = direction;
        bufferX[steps] = traceX;
        bufferY[steps++] = traceY;
        while (traceX != start.getX() || traceY != start.getY()) {
            if (lastwritten != direction) {
                bufferX[steps] = traceX;
                bufferY[steps++] = traceY;
                lastwritten = direction;
            }

            if ((direction & DIR_EAST) != 0)
                traceX++;
            else if ((direction & DIR_WEST) != 0)
                traceX--;

            if ((direction & DIR_NORTH) != 0)
                traceY++;
            else if ((direction & DIR_SOUTH) != 0)
                traceY--;

            direction = directions[traceX - graphBaseX][traceY - graphBaseY];
        }
        List<WorldTile> stepList = new ArrayList<>();
        WorldTile curr = new WorldTile(start.getX(), start.getY(), start.getZ());
        for (int i = steps - 1; i >= 0; i--) {
            int destX = bufferX[i];
            int destY = bufferY[i];
            do {
                if (curr.getX() < destX)
                    curr = curr.transform(1, 0);
                else if (curr.getX() > destX)
                    curr = curr.transform(-1, 0);
                if (curr.getY() < destY)
                    curr = curr.transform(0, 1);
                else if (curr.getY() > destY)
                    curr = curr.transform(0, -1);
                stepList.add(curr.transform(0, 0));
            } while (curr.getX() != destX || curr.getY() != destY);
        }

        return stepList;
    }

    private static boolean checkSingleTraversal(int srcX, int srcY, RouteStrategy strategy) {
        int[][] _directions = directions;
        int[][] _distances = distances;
        int[][] _clip = clip;
        int[] _bufferX = bufferX;
        int[] _bufferY = bufferY;

        int graphBaseX = srcX - (GRAPH_SIZE / 2);
        int graphBaseY = srcY - (GRAPH_SIZE / 2);
        int currentX = srcX;
        int currentY = srcY;
        int currentGraphX = srcX - graphBaseX;
        int currentGraphY = srcY - graphBaseY;

        _distances[currentGraphX][currentGraphY] = 0;
        _directions[currentGraphX][currentGraphY] = 99;

        int read = 0, write = 0;
        _bufferX[write] = currentX;
        _bufferY[write++] = currentY;

        while (read != write) {
            currentX = _bufferX[read];
            currentY = _bufferY[read];
            read = (read + 1) & (QUEUE_SIZE - 1);

            currentGraphX = currentX - graphBaseX;
            currentGraphY = currentY - graphBaseY;

            if (strategy.canExit(currentX, currentY, 1, _clip, graphBaseX, graphBaseY)) {
                exitX = currentX;
                exitY = currentY;
                return true;
            }

            int nextDistance = _distances[currentGraphX][currentGraphY] + 1;
            if (currentGraphX > 0 && _directions[currentGraphX - 1][currentGraphY] == 0 && !ClipFlag.flagged(_clip[currentGraphX - 1][currentGraphY], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_E)) {
                _bufferX[write] = currentX - 1;
                _bufferY[write] = currentY;
                write = (write + 1) & (QUEUE_SIZE - 1);

                _directions[currentGraphX - 1][currentGraphY] = DIR_EAST;
                _distances[currentGraphX - 1][currentGraphY] = nextDistance;
            }
            if (currentGraphX < (GRAPH_SIZE - 1) && _directions[currentGraphX + 1][currentGraphY] == 0 && !ClipFlag.flagged(_clip[currentGraphX + 1][currentGraphY], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_W)) {
                _bufferX[write] = currentX + 1;
                _bufferY[write] = currentY;
                write = (write + 1) & (QUEUE_SIZE - 1);

                _directions[currentGraphX + 1][currentGraphY] = DIR_WEST;
                _distances[currentGraphX + 1][currentGraphY] = nextDistance;
            }
            if (currentGraphY > 0 && _directions[currentGraphX][currentGraphY - 1] == 0 && !ClipFlag.flagged(_clip[currentGraphX][currentGraphY - 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N)) {
                _bufferX[write] = currentX;
                _bufferY[write] = currentY - 1;
                write = (write + 1) & (QUEUE_SIZE - 1);

                _directions[currentGraphX][currentGraphY - 1] = DIR_NORTH;
                _distances[currentGraphX][currentGraphY - 1] = nextDistance;
            }
            if (currentGraphY < (GRAPH_SIZE - 1) && _directions[currentGraphX][currentGraphY + 1] == 0 && !ClipFlag.flagged(_clip[currentGraphX][currentGraphY + 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_S)) {
                _bufferX[write] = currentX;
                _bufferY[write] = currentY + 1;
                write = (write + 1) & (QUEUE_SIZE - 1);

                _directions[currentGraphX][currentGraphY + 1] = DIR_SOUTH;
                _distances[currentGraphX][currentGraphY + 1] = nextDistance;
            }
            if (currentGraphX > 0 && currentGraphY > 0 && _directions[currentGraphX - 1][currentGraphY - 1] == 0 && !ClipFlag.flagged(_clip[currentGraphX - 1][currentGraphY - 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_E, ClipFlag.PF_NE)
                    && !ClipFlag.flagged(_clip[currentGraphX - 1][currentGraphY], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_E)
                    && !ClipFlag.flagged(clip[currentGraphX][currentGraphY - 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N)) {
                _bufferX[write] = currentX - 1;
                _bufferY[write] = currentY - 1;
                write = (write + 1) & (QUEUE_SIZE - 1);

                _directions[currentGraphX - 1][currentGraphY - 1] = DIR_NORTH | DIR_EAST;
                _distances[currentGraphX - 1][currentGraphY - 1] = nextDistance;
            }
            if (currentGraphX < (GRAPH_SIZE - 1) && currentGraphY > 0 && _directions[currentGraphX + 1][currentGraphY - 1] == 0
                    && !ClipFlag.flagged(_clip[currentGraphX + 1][currentGraphY - 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_W, ClipFlag.PF_NW)
                    && !ClipFlag.flagged(_clip[currentGraphX + 1][currentGraphY], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_W)
                    && !ClipFlag.flagged(_clip[currentGraphX][currentGraphY - 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N)) {
                _bufferX[write] = currentX + 1;
                _bufferY[write] = currentY - 1;
                write = (write + 1) & (QUEUE_SIZE - 1);

                _directions[currentGraphX + 1][currentGraphY - 1] = DIR_NORTH | DIR_WEST;
                _distances[currentGraphX + 1][currentGraphY - 1] = nextDistance;
            }
            if (currentGraphX > 0 && currentGraphY < (GRAPH_SIZE - 1) && _directions[currentGraphX - 1][currentGraphY + 1] == 0
                    && !ClipFlag.flagged(_clip[currentGraphX - 1][currentGraphY + 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_E, ClipFlag.PF_S, ClipFlag.PF_SE)
                    && !ClipFlag.flagged(_clip[currentGraphX - 1][currentGraphY], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_E)
                    && !ClipFlag.flagged(_clip[currentGraphX][currentGraphY + 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_S)) {
                _bufferX[write] = currentX - 1;
                _bufferY[write] = currentY + 1;
                write = (write + 1) & (QUEUE_SIZE - 1);

                _directions[currentGraphX - 1][currentGraphY + 1] = DIR_SOUTH | DIR_EAST;
                _distances[currentGraphX - 1][currentGraphY + 1] = nextDistance;
            }
            if (currentGraphX < (GRAPH_SIZE - 1) && currentGraphY < (GRAPH_SIZE - 1) && _directions[currentGraphX + 1][currentGraphY + 1] == 0
                    && !ClipFlag.flagged(_clip[currentGraphX + 1][currentGraphY + 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_S, ClipFlag.PF_W, ClipFlag.PF_SW)
                    && !ClipFlag.flagged(_clip[currentGraphX + 1][currentGraphY], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_W)
                    && !ClipFlag.flagged(_clip[currentGraphX][currentGraphY + 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_S)) {
                _bufferX[write] = currentX + 1;
                _bufferY[write] = currentY + 1;
                write = (write + 1) & (QUEUE_SIZE - 1);

                _directions[currentGraphX + 1][currentGraphY + 1] = DIR_SOUTH | DIR_WEST;
                _distances[currentGraphX + 1][currentGraphY + 1] = nextDistance;
            }

        }
        exitX = currentX;
        exitY = currentY;
        return false;
    }

    private static boolean checkDoubleTraversal(int srcX, int srcY, RouteStrategy strategy) {
        return checkVariableTraversal(srcX, srcY, 2, strategy);
    }

    private static boolean checkVariableTraversal(int srcX, int srcY, int size, RouteStrategy strategy) {
        int[][] _directions = directions;
        int[][] _distances = distances;
        int[][] _clip = clip;
        int[] _bufferX = bufferX;
        int[] _bufferY = bufferY;

        int graphBaseX = srcX - (GRAPH_SIZE / 2);
        int graphBaseY = srcY - (GRAPH_SIZE / 2);
        int currentX = srcX;
        int currentY = srcY;
        int currentGraphX = srcX - graphBaseX;
        int currentGraphY = srcY - graphBaseY;

        _distances[currentGraphX][currentGraphY] = 0;
        _directions[currentGraphX][currentGraphY] = 99;

        int read = 0, write = 0;
        _bufferX[write] = currentX;
        _bufferY[write++] = currentY;

        while (read != write) {
            currentX = _bufferX[read];
            currentY = _bufferY[read];
            read = (read + 1) & (QUEUE_SIZE - 1);

            currentGraphX = currentX - graphBaseX;
            currentGraphY = currentY - graphBaseY;

            if (strategy.canExit(currentX, currentY, size, _clip, graphBaseX, graphBaseY)) {
                exitX = currentX;
                exitY = currentY;
                return true;
            }

            int nextDistance = _distances[currentGraphX][currentGraphY] + 1;
            if (currentGraphX > 0 && _directions[currentGraphX - 1][currentGraphY] == 0
                    && !ClipFlag.flagged(_clip[currentGraphX - 1][currentGraphY], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_E, ClipFlag.PF_NE)
                    && !ClipFlag.flagged(_clip[currentGraphX - 1][currentGraphY + (size - 1)], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_E, ClipFlag.PF_S, ClipFlag.PF_SE)) {
                exit:
                do {
                    for (int y = 1; y < (size - 1); y++) {
                        if (ClipFlag.flagged(_clip[currentGraphX - 1][currentGraphY + y], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_E, ClipFlag.PF_S, ClipFlag.PF_NE, ClipFlag.PF_SE))
                            break exit;
                    }
                    _bufferX[write] = currentX - 1;
                    _bufferY[write] = currentY;
                    write = (write + 1) & (QUEUE_SIZE - 1);

                    _directions[currentGraphX - 1][currentGraphY] = DIR_EAST;
                    _distances[currentGraphX - 1][currentGraphY] = nextDistance;
                } while (false);
            }
            if (currentGraphX < (GRAPH_SIZE - size) && _directions[currentGraphX + 1][currentGraphY] == 0
                    && !ClipFlag.flagged(_clip[currentGraphX + size][currentGraphY], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_W, ClipFlag.PF_NW)
                    && !ClipFlag.flagged(_clip[currentGraphX + size][currentGraphY + (size - 1)], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_S, ClipFlag.PF_W, ClipFlag.PF_SW)) {
                exit:
                do {
                    for (int y = 1; y < (size - 1); y++) {
                        if (ClipFlag.flagged(_clip[currentGraphX + size][currentGraphY + y], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_S, ClipFlag.PF_W, ClipFlag.PF_NW, ClipFlag.PF_SW))
                            break exit;
                    }
                    _bufferX[write] = currentX + 1;
                    _bufferY[write] = currentY;
                    write = (write + 1) & (QUEUE_SIZE - 1);

                    _directions[currentGraphX + 1][currentGraphY] = DIR_WEST;
                    _distances[currentGraphX + 1][currentGraphY] = nextDistance;
                } while (false);
            }
            if (currentGraphY > 0 && _directions[currentGraphX][currentGraphY - 1] == 0
                    && !ClipFlag.flagged(_clip[currentGraphX][currentGraphY - 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_E, ClipFlag.PF_NE)
                    && !ClipFlag.flagged(_clip[currentGraphX + (size - 1)][currentGraphY - 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_W, ClipFlag.PF_NW)) {
                exit:
                do {
                    for (int y = 1; y < (size - 1); y++) {
                        if (ClipFlag.flagged(_clip[currentGraphX + y][currentGraphY - 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_E, ClipFlag.PF_W, ClipFlag.PF_NW, ClipFlag.PF_NE))
                            break exit;
                    }
                    _bufferX[write] = currentX;
                    _bufferY[write] = currentY - 1;
                    write = (write + 1) & (QUEUE_SIZE - 1);

                    _directions[currentGraphX][currentGraphY - 1] = DIR_NORTH;
                    _distances[currentGraphX][currentGraphY - 1] = nextDistance;
                } while (false);
            }
            if (currentGraphY < (GRAPH_SIZE - size) && _directions[currentGraphX][currentGraphY + 1] == 0
                    && !ClipFlag.flagged(_clip[currentGraphX][currentGraphY + size], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_E, ClipFlag.PF_S, ClipFlag.PF_SE)
                    && !ClipFlag.flagged(_clip[currentGraphX + (size - 1)][currentGraphY + size], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_S, ClipFlag.PF_W, ClipFlag.PF_SW)) {
                exit:
                do {
                    for (int y = 1; y < (size - 1); y++) {
                        if (ClipFlag.flagged(_clip[currentGraphX + y][currentGraphY + size], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_E, ClipFlag.PF_S, ClipFlag.PF_W, ClipFlag.PF_SE, ClipFlag.PF_SW))
                            break exit;
                    }
                    _bufferX[write] = currentX;
                    _bufferY[write] = currentY + 1;
                    write = (write + 1) & (QUEUE_SIZE - 1);

                    _directions[currentGraphX][currentGraphY + 1] = DIR_SOUTH;
                    _distances[currentGraphX][currentGraphY + 1] = nextDistance;
                } while (false);
            }
            if (currentGraphX > 0 && currentGraphY > 0 && _directions[currentGraphX - 1][currentGraphY - 1] == 0
                    && !ClipFlag.flagged(_clip[currentGraphX - 1][currentGraphY - 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_E, ClipFlag.PF_NE)) {
                exit:
                do {
                    for (int y = 1; y < size; y++) {
                        if (ClipFlag.flagged(_clip[currentGraphX - 1][currentGraphY + (y - 1)], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_E, ClipFlag.PF_S, ClipFlag.PF_NE, ClipFlag.PF_SE)
                                || ClipFlag.flagged(_clip[currentGraphX + (y - 1)][currentGraphY - 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_E, ClipFlag.PF_W, ClipFlag.PF_NW, ClipFlag.PF_NE))
                            break exit;
                    }
                    _bufferX[write] = currentX - 1;
                    _bufferY[write] = currentY - 1;
                    write = (write + 1) & (QUEUE_SIZE - 1);

                    _directions[currentGraphX - 1][currentGraphY - 1] = DIR_NORTH | DIR_EAST;
                    _distances[currentGraphX - 1][currentGraphY - 1] = nextDistance;
                } while (false);
            }
            if (currentGraphX < (GRAPH_SIZE - size) && currentGraphY > 0 && _directions[currentGraphX + 1][currentGraphY - 1] == 0
                    && !ClipFlag.flagged(_clip[currentGraphX + size][currentGraphY - 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_W, ClipFlag.PF_NW)) {
                exit:
                do {
                    for (int y = 1; y < size; y++) {
                        if (ClipFlag.flagged(_clip[currentGraphX + size][currentGraphY + (y - 1)], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_S, ClipFlag.PF_W, ClipFlag.PF_NW, ClipFlag.PF_SW)
                                || ClipFlag.flagged(_clip[currentGraphX + y][currentGraphY - 1], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_E, ClipFlag.PF_W, ClipFlag.PF_NW, ClipFlag.PF_NE))
                            break exit;
                    }
                    _bufferX[write] = currentX + 1;
                    _bufferY[write] = currentY - 1;
                    write = (write + 1) & (QUEUE_SIZE - 1);

                    _directions[currentGraphX + 1][currentGraphY - 1] = DIR_NORTH | DIR_WEST;
                    _distances[currentGraphX + 1][currentGraphY - 1] = nextDistance;
                } while (false);
            }
            if (currentGraphX > 0 && currentGraphY < (GRAPH_SIZE - size) && _directions[currentGraphX - 1][currentGraphY + 1] == 0
                    && !ClipFlag.flagged(_clip[currentGraphX - 1][currentGraphY + size], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_E, ClipFlag.PF_S, ClipFlag.PF_SE)) {
                exit:
                do {
                    for (int y = 1; y < size; y++) {
                        if (ClipFlag.flagged(_clip[currentGraphX - 1][currentGraphY + y], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_E, ClipFlag.PF_S, ClipFlag.PF_NE, ClipFlag.PF_SE)
                                || ClipFlag.flagged(_clip[currentGraphX + (y - 1)][currentGraphY + size], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_E, ClipFlag.PF_S, ClipFlag.PF_W, ClipFlag.PF_SE, ClipFlag.PF_SW))
                            break exit;
                    }
                    _bufferX[write] = currentX - 1;
                    _bufferY[write] = currentY + 1;
                    write = (write + 1) & (QUEUE_SIZE - 1);

                    _directions[currentGraphX - 1][currentGraphY + 1] = DIR_SOUTH | DIR_EAST;
                    _distances[currentGraphX - 1][currentGraphY + 1] = nextDistance;
                } while (false);
            }
            if (currentGraphX < (GRAPH_SIZE - size) && currentGraphY < (GRAPH_SIZE - size) && _directions[currentGraphX + 1][currentGraphY + 1] == 0
                    && !ClipFlag.flagged(_clip[currentGraphX + size][currentGraphY + size], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_S, ClipFlag.PF_W, ClipFlag.PF_SW)) {
                exit:
                do {
                    for (int y = 1; y < size; y++) {
                        if (ClipFlag.flagged(_clip[currentGraphX + y][currentGraphY + size], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_E, ClipFlag.PF_S, ClipFlag.PF_W, ClipFlag.PF_SE, ClipFlag.PF_SW)
                                || ClipFlag.flagged(_clip[currentGraphX + size][currentGraphY + y], ClipFlag.PFBW_FLOOR, ClipFlag.PFBW_GROUND_DECO, ClipFlag.PF_FULL, ClipFlag.PF_N, ClipFlag.PF_S, ClipFlag.PF_W, ClipFlag.PF_NW, ClipFlag.PF_SW))
                            break exit;
                    }
                    _bufferX[write] = currentX + 1;
                    _bufferY[write] = currentY + 1;
                    write = (write + 1) & (QUEUE_SIZE - 1);

                    _directions[currentGraphX + 1][currentGraphY + 1] = DIR_SOUTH | DIR_WEST;
                    _distances[currentGraphX + 1][currentGraphY + 1] = nextDistance;
                } while (false);
            }

        }

        exitX = currentX;
        exitY = currentY;
        return false;
    }

    private static void transmitClipData(int x, int y, int z) {
        int graphBaseX = x - (GRAPH_SIZE / 2);
        int graphBaseY = y - (GRAPH_SIZE / 2);

        for (int transmitRegionX = graphBaseX >> 6; transmitRegionX <= (graphBaseX + (GRAPH_SIZE - 1)) >> 6; transmitRegionX++) {
            for (int transmitRegionY = graphBaseY >> 6; transmitRegionY <= (graphBaseY + (GRAPH_SIZE - 1)) >> 6; transmitRegionY++) {
                int startX = Math.max(graphBaseX, transmitRegionX << 6), startY = Math.max(graphBaseY, transmitRegionY << 6);
                int endX = Math.min(graphBaseX + GRAPH_SIZE, (transmitRegionX << 6) + 64), endY = Math.min(graphBaseY + GRAPH_SIZE, (transmitRegionY << 6) + 64);
                Region region = Region.get(transmitRegionX << 8 | transmitRegionY);
                if (region.getClipMap() == null || region.getClipMap().getMasks() == null) {
                    for (int fillX = startX; fillX < endX; fillX++)
                        for (int fillY = startY; fillY < endY; fillY++)
                            clip[fillX - graphBaseX][fillY - graphBaseY] = -1;
                } else {
                    int[][] masks = region.getClipMap().getMasks()[z];
                    for (int fillX = startX; fillX < endX; fillX++) {
                        for (int fillY = startY; fillY < endY; fillY++) {
                            clip[fillX - graphBaseX][fillY - graphBaseY] = masks[fillX & 0x3F][fillY & 0x3F];
                        }
                    }
                }
            }
        }
    }

    protected static int[] getLastPathBufferX() {
        return bufferX;
    }

    protected static int[] getLastPathBufferY() {
        return bufferY;
    }

    protected static boolean lastIsAlternative() {
        return isAlternative;
    }

    public static int[][] getClip() {
        return clip;
    }
}
