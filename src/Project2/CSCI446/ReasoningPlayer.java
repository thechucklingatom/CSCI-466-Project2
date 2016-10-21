package Project2.CSCI446;

import Exceptions.OutOfArrowsException;

import java.util.List;

/**
 * Created by Alan Fraticelli on 10/20/2016.
 */
public class ReasoningPlayer extends Player{
    private InferenceEngine logic;
    private KnowledgeBase[][] map;
    private int curX;
    private int curY;
    private List<Percept> currentPercept;
    private boolean solved = false;

    public ReasoningPlayer(int numArrows, Room inRoom, World theWorld){
        arrowCount = numArrows;
        currentRoom = inRoom;
        world = theWorld;
        map = new KnowledgeBase[55][55];
        for (int i = 0; i < map.length; i++) {

            for (int i1 = 0; i1 < map.length; i1++) {
                map[i][i1] = new KnowledgeBase();
            }
        }
        logic = new InferenceEngine(map);
        curX = 26;
        curY = 26;
    }

    //when we move, make sure to update currentRoom
    @Override
    public void solve() {
        //while loop that continues until GOAL
        do {
            //mark visited
            map[curX][curY].visited = true;
            //get percepts
            currentPercept = currentRoom.getPercepts();

            //update the predicates
            boolean smelly = false, windy = false, glitter = false;
            for (Percept percept : currentPercept) {
                //the percepts we send to update is the ones that are FALSE

                if (percept == Percept.SMELLY) {
                    smelly = true;
                }
                if (percept == Percept.WINDY) {
                    windy = true;
                } //check for the goal state
                if (logic.checkGold(curX, curY, percept)){
                    glitter = true;
                    pickUpGold();
                    return;
                }
            }
            //for each adjacent, call update(map[][], p) where p is a false windy/smelly
            if(!smelly){
                logic.update(map[curX+1][curY], Percept.SMELLY);
                logic.update(map[curX][curY+1], Percept.SMELLY);
                logic.update(map[curX-1][curY], Percept.SMELLY);
                logic.update(map[curX][curY-1], Percept.SMELLY);
            }
            if(!windy){
                logic.update(map[curX+1][curY], Percept.WINDY);
                logic.update(map[curX][curY+1], Percept.WINDY);
                logic.update(map[curX-1][curY], Percept.WINDY);
                logic.update(map[curX][curY-1], Percept.WINDY);
            }
            //iterate percepts to see if we can set squares to-
            //true variables if we have stink/breeze percepts
            for(Percept percept : currentPercept){
                if(smelly){
                    logic.checkIfTrue(curX, curY, RoomType.WUMPUS);
                }
                if(windy){
                    logic.checkIfTrue(curX, curY, RoomType.PIT);
                }
            }
            //---WE ARE NOW INFERING MOVE---
            //check if we can shoot a wumpus
            Percept success = null;
            if(logic.canShootWumpus(curX, curY, direction) && arrowCount != 0) {
                //if true, then shoot
                try{
                    success = shoot();
                } catch (OutOfArrowsException e){}
                arrowCount--;
                if(success == Percept.SCREAM){
                    logic.inferDeadWumpus();
                }
            }
            //if we're in a stink/breeze, then we BACKTRACK
            //if we are not:
            //start at the forward square, check for visited or obstacle
            boolean hasMoved = true;
            if(!checkForward()){
                turnRight();
                //then right
                if(!checkForward()){
                    turnLeft();
                    turnLeft();
                    if(!checkForward()){
                        hasMoved = false;
                    }
                }
            }

            //if all these fail, spiral outwards until you hit non-visted square
            //REVISIT THIS IDEA FOR PATHFINDING
            //got the square, facing it, check canMove(direction)
            //if bump, tell(T, OBSTACLE) and mark visited
            //if death, tell(T, p[what killed you])
            //if null, move(direction)
        } while (solved == false); //end of loop
    }

    public void move(Direction d){
        switch(d){
            case EAST:
                curX++;
                break;
            case NORTH:
                curY++;
                break;
            case WEST:
                curX--;
                break;
            case SOUTH:
                curY--;
                break;
        }
    }

    public int[] tempMove(Direction d){
        int[] position = {curX, curY};
        switch (d){
            case EAST:
                position[0]++;
                break;
            case NORTH:
                position[1]++;
                break;
            case WEST:
                position[0]--;
                break;
            case SOUTH:
                position[1]--;
        }
        return position;
    }
    //this will check if we can try to move forward and will return a boolean with that fact
    //then tries to move forward
    public boolean checkForward(){
        int[] tempXY = tempMove(direction);
        if(map[tempXY[0]][tempXY[1]].ask(RoomType.OBSTACLE) != Truth.TRUE &&
                !map[tempXY[0]][tempXY[1]].visited) {
            //if not visited or obstacle, move forward
            //if so, try forward...
            if (logic.isSafe(curX, curY, direction)) {
                RoomType nextType = world.canMove(direction);
                if (nextType == null) {
                    world.move(direction);
                    move(direction);
                } else if (nextType == RoomType.OBSTACLE) {
                    map[tempXY[0]][tempXY[1]].tell(Truth.TRUE, RoomType.OBSTACLE);
                }
            }
            return true;
        }
        return false;
    }

    public void pickUpGold(){
        solved = true;
    }

    @Override
    public Percept shoot() throws OutOfArrowsException {
        return world.shoot(direction);
    }
}
