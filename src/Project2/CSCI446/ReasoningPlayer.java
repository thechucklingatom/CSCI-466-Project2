package Project2.CSCI446;

import Exceptions.OutOfArrowsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

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
    private Stack<Move> moveStack;
    private boolean spiralLeft = true;

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
        moveStack = new Stack<Move>();
    }

    //when we move, make sure to update currentRoom
    @Override
    public void solve() {
        //while loop that continues until GOAL
        do {
            //mark visited
            map[curX][curY].visited = true;
            map[curX][curY].tell(Truth.TRUE, RoomType.EMPTY);
            map[curX][curY].tell(Truth.FALSE, RoomType.OBSTACLE);
            map[curX][curY].tell(Truth.FALSE, RoomType.PIT);
            map[curX][curY].tell(Truth.FALSE, RoomType.WUMPUS);
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
                if (logic.checkGold(curX, curY, percept)) {
                    glitter = true;
                    pickUpGold();
                    return;
                }
            }
            //for each adjacent, call update(map[][], p) where p is a false windy/smelly
            if (!smelly) {
                logic.update(map[curX + 1][curY], Percept.SMELLY);
                logic.update(map[curX][curY + 1], Percept.SMELLY);
                logic.update(map[curX - 1][curY], Percept.SMELLY);
                logic.update(map[curX][curY - 1], Percept.SMELLY);
            }
            if (!windy) {
                logic.update(map[curX + 1][curY], Percept.WINDY);
                logic.update(map[curX][curY + 1], Percept.WINDY);
                logic.update(map[curX - 1][curY], Percept.WINDY);
                logic.update(map[curX][curY - 1], Percept.WINDY);
            }
            //iterate percepts to see if we can set squares to-
            //true variables if we have stink/breeze percepts
            for (Percept percept : currentPercept) {
                if (smelly) {
                    logic.checkIfTrue(curX, curY, RoomType.WUMPUS);
                }
                if (windy) {
                    logic.checkIfTrue(curX, curY, RoomType.PIT);
                }
            }
            //---WE ARE NOW INFERING MOVE---
            //check if we can shoot a wumpus
            Percept success = null;
            if (logic.canShootWumpus(curX, curY, direction) && arrowCount != 0) {
                //if true, then shoot
                try {
                    success = shoot();
                } catch (OutOfArrowsException e) {
                }
                arrowCount--;
                if (success == Percept.SCREAM) {
                    logic.inferDeadWumpus();
                }
            }
            //if we're in a stink/breeze, then we BACKTRACK
            //if we are not:
            //start at the forward square, check for visited or obstacle
            boolean hasMoved = true;
            if (!checkForward()) {
                turnRight();
                moveStack.push(Move.TURNRIGHT);
                //then right
                if (!checkForward()) {
                    turnLeft();
                    turnLeft();
                    moveStack.push(Move.TURNLEFT);
                    moveStack.push(Move.TURNLEFT);
                    if (!checkForward()) {
                        turnLeft();
                        moveStack.push(Move.TURNLEFT);
                        if(!checkForward()) {
                            hasMoved = false;
                        }
                    }
                }
            }
            //we failed to move in a direction of an unvisited, safe square, backtrack
            if(!hasMoved) {
                if (backtracking()) {
                    //if backtracking finds an unvisited safe square, return true
                    //means that we can now just loop again
                    //if we reach backtracking stack size 0, then we did not find an unvisited safe square
                    //in the else, we will then test dangerous squares, as they are the last places to go
                } else {
                    int counter = 0;
                    boolean keepSpiraling = true;
                    while (keepSpiraling) {
                        for (int i = 0; i < counter; i++) {
                            if(world.canMove(direction) == null) {
                                if(getTimesVisited(Move.TURNLEFT) >= getTimesVisited(Move.FORWARD)
                                        && getTimesVisited(Move.TURNRIGHT) >= getTimesVisited(Move.FORWARD)) {
                                    move(direction);
                                    currentRoom = world.move(direction);
                                    moveStack.push(Move.FORWARD);
                                    map[curX][curY].timesVisted++;
                                    List<Direction> directionsOfUnvisted = checkSurroundingRooms();
                                    if ((!map[curX][curY].visited ) ||
                                            (currentRoom.getPercepts().contains(Percept.GLITTER))) {
                                        map[curX][curY].visited = true;
                                        keepSpiraling = false;
                                        break;
                                    }
                                }else{
                                   break;
                                }
                            }else{
                                int[] nextRoom;
                                switch(world.canMove(direction) == null ? RoomType.EMPTY : world.canMove(direction)){
                                    case OBSTACLE:
                                        nextRoom = tempMove(direction);
                                        map[nextRoom[0]][nextRoom[1]].tell(Truth.TRUE, RoomType.OBSTACLE);
                                        map[nextRoom[0]][nextRoom[1]].visited = true;
                                        map[nextRoom[0]][nextRoom[1]].timesVisted++;
                                        break;
                                    case WUMPUS:
                                        nextRoom = tempMove(direction);
                                        map[nextRoom[0]][nextRoom[1]].tell(Truth.TRUE, RoomType.WUMPUS);
                                        deaths.add(RoomType.WUMPUS);
                                        currentRoom = world.move(direction);
                                        move(direction);
                                        moveStack.push(Move.FORWARD);
                                        map[curX][curY].timesVisted++;
                                        totalCost -= 1000;
                                        try {
                                            success = shoot();
                                        } catch (OutOfArrowsException e) {
                                        }
                                        arrowCount--;
                                        if (success == Percept.SCREAM) {
                                            logic.inferDeadWumpus();
                                        }
                                        break;
                                    case PIT:
                                        nextRoom = tempMove(direction);
                                        map[nextRoom[0]][nextRoom[1]].tell(Truth.TRUE, RoomType.PIT);
                                        map[nextRoom[0]][nextRoom[1]].timesVisted++;
                                        deaths.add(RoomType.PIT);
                                        totalCost -= 1000;
                                        break;
                                }


                                break;
                            }
                        }

                        if(getTimesVisited(Move.TURNLEFT) <= getTimesVisited(Move.TURNRIGHT)) {
                            turnLeft();
                            moveStack.push(Move.TURNLEFT);
                        }else{
                            turnRight();
                            moveStack.push(Move.TURNRIGHT);
                        }
                        counter++;
                    }
                    spiralLeft = !spiralLeft;
                }
            }
        } while (haveGold == false); //end of loop
    }

    public List<Direction> checkSurroundingRooms(){
        ArrayList<Direction> toReturn = new ArrayList();
         if(!map[curX + 1][curY].visited){
             toReturn.add(Direction.EAST);
         }

         if(!map[curX - 1][curY].visited){
             toReturn.add(Direction.WEST);
         }

         if(!map[curX][curY - 1].visited){
             toReturn.add(Direction.SOUTH);
         }

         if(!map[curX][curY + 1].visited){
             toReturn.add(Direction.NORTH);
         }

        return toReturn;
    }

    public void move(Direction d){
        totalCost -= 1;
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
                    currentRoom = world.move(direction);
                    move(direction);
                    moveStack.push(Move.FORWARD);
                    return true;
                } else if (nextType == RoomType.OBSTACLE) {
                    map[tempXY[0]][tempXY[1]].tell(Truth.TRUE, RoomType.OBSTACLE);
                    map[tempXY[0]][tempXY[1]].tell(Truth.FALSE, RoomType.WUMPUS);
                    map[tempXY[0]][tempXY[1]].tell(Truth.FALSE, RoomType.PIT);
                    map[tempXY[0]][tempXY[1]].visited = true;
                    totalCost--;
                }
            }
        }
        return false;
    }

    public boolean backtracking(){
        //turn left twice to always do a 180. Since the original turns will be undone.
        turnLeft();
        turnLeft();
        while(!moveStack.empty()){
            Move curMove = moveStack.pop();
            switch(curMove){
                case FORWARD:
                    currentRoom = world.move(direction);
                    move(direction);
                    totalCost--;
                    if(logic.nearUnvisited(curX, curY)){
                        turnLeft();
                        turnLeft();
                        return true;
                    }
                    break;
                case TURNLEFT:
                    turnRight();
                    break;
                case TURNRIGHT:
                    turnLeft();
                    break;
            }
        }
        turnLeft();
        turnLeft();
        return false;
    }

    @Override
    public Percept shoot() throws OutOfArrowsException {
        return world.shoot(direction);
    }

    public void turnToFace(Direction direction){
        while(direction != this.direction){
            turnLeft();
            moveStack.push(Move.TURNLEFT);
        }
    }

    public int getTimesVisited(Move move){
        if(move == Move.TURNLEFT){
            turnLeft();
            totalCost++;
            int[] temp = tempMove(direction);
            turnRight();
            totalCost++;
            return map[temp[0]][temp[1]].timesVisted;
        }else if(move == Move.TURNRIGHT){
            turnRight();
            totalCost++;
            int[] temp = tempMove(direction);
            turnLeft();
            totalCost++;
            return map[temp[0]][temp[1]].timesVisted;
        }else{
            int[] temp = tempMove(direction);
            return map[temp[0]][temp[1]].timesVisted;
        }
    }
}
