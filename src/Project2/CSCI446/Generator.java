package Project2.CSCI446;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Mathew Gostnell on 10/16/2016.
 */
public class Generator {
    private Room[][] world;
    private Random rng;
    private int worldSize;
    private double pitProb;
    private double wumpusProb;
    private double obstacleProb;
    private int startPoint;

    public Generator(int worldSize, double pitProb, double wumpusProb, double obstacleProb) {
        this.worldSize = worldSize;
        this.pitProb = pitProb;
        this.wumpusProb = wumpusProb;
        this.obstacleProb = obstacleProb;
        this.rng = new Random();
        world = new Room[worldSize][worldSize];
        startPoint = -1;

        this.GenerateWorld();
    }

    private void GenerateWorld() {
        for (int row = 0; row < worldSize; row++) {
            for (int column = 0; column < worldSize; column++) {
                double rnd = rng.nextFloat();
                // randomly add possible RoomTypes based on probability
                List<RoomType> roomRoles = new ArrayList<RoomType>();
                if (rnd < pitProb) {
                    roomRoles.add(RoomType.PIT);
                }
                if (rnd < wumpusProb) {
                    roomRoles.add(RoomType.WUMPUS);
                }
                if (rnd < obstacleProb) {
                    roomRoles.add(RoomType.OBSTACLE);
                }
                // add an empty room by default
                roomRoles.add(RoomType.EMPTY);

                // update current room with randomly selected RoomType and current position
                int selection = rng.nextInt(roomRoles.size());
                RoomType selRoom = roomRoles.get(selection);
                world[row][column].setType(selRoom);
                world[row][column].setXPosition(row);
                world[row][column].setYPosition(column);

                // update percepts in adj. Rooms
                switch (selRoom) {
                    case PIT:
                        updatePercepts(row, column, Percept.WINDY);
                        break;

                    case OBSTACLE:
                        updatePercepts(row, column, Percept.BUMP);
                        break;

                    case WUMPUS:
                        updatePercepts(row, column, Percept.SMELLY);
                        break;
                }
            }
        }

        // randomly select a point to house the Gold if it is empty
        RoomType currentType;
        int xPos;
        int yPos;
        do {
            xPos = rng.nextInt(worldSize);
            yPos = rng.nextInt(worldSize);
            currentType = world[xPos][yPos].getType();
        } while (currentType != RoomType.EMPTY);

        // set goal state
        world[xPos][yPos].setType(RoomType.GOLD);

        // randomly select a start point for the Explorer
        do {
            xPos = rng.nextInt(worldSize);
            yPos = rng.nextInt(worldSize);
            currentType = world[xPos][yPos].getType();
        } while (currentType != RoomType.EMPTY && !hasValidPath(xPos, yPos));

        // set start point as an integer
        startPoint = xPos * (worldSize + 1) + yPos;
    }

    public int startXPosition() {
        return startPoint / (worldSize + 1);
    }

    public int startYPosition() {
        return startPoint % (worldSize + 1);
    }

    private boolean hasValidPath(int x, int y) {
        if (world[x][y].getType() == RoomType.GOLD) {
            return true;
        } else if (world[x][y].getType() != RoomType.EMPTY) {
            return false;
        } else {
            boolean lRoom = false, rRoom = false, uRoom = false, dRoom = false;
            if (x > 0) {
                lRoom = hasValidPath(x-1, y);
            }
            if (x + 1 < worldSize) {
                rRoom = hasValidPath(x+1, y);
            }
            if (y > 0) {
                dRoom = hasValidPath(x, y-1);
            }
            if (y + 1 < worldSize) {
                uRoom = hasValidPath(x, y+1);
            }

            return lRoom || rRoom || uRoom || dRoom;
        }
    }

    public Room[][] getWorld() {
        return world;
    }

    public World getWorldAsWorld(){
        return new World(world, startXPosition(), startYPosition());
    }

    private void updatePercepts(int x, int y, Percept sense) {
        if (x + 1 < worldSize) {
            // we have adj. Room to the right of this point
            world[x+1][y].addPercept(sense);
        }
        if (x > 0) {
            // we have adj. Room to the left of this point
            world[x-1][y].addPercept(sense);
        }

        if (y + 1 < worldSize) {
            // we have adj. Room above this point
            world[x][y+1].addPercept(sense);
        }

        if (y > 0) {
            // we have adj. Room below this point
            world[x][y-1].addPercept(sense);
        }
    }
}
