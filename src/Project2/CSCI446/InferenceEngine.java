package Project2.CSCI446;

/**
 * Created by Alan Fraticelli on 10/20/2016.
 */
public class InferenceEngine {
    KnowledgeBase[][] map;
    int tempXWumpus, tempYWumpus;

    public InferenceEngine(KnowledgeBase[][] inMap){
        map = inMap;
    }

    //we are looking at an adjacent square when we call this method
    public void update(KnowledgeBase base, Percept percept){
        //if breeze/stink are false:
        //get predicate that is related to percept
        //call ask(p)
        //if return is MAYBE, tell(F, p)
        if(percept == Percept.SMELLY){
            Truth answer = base.ask(RoomType.WUMPUS);
            if(answer == Truth.MAYBE){
                base.tell(Truth.FALSE, RoomType.WUMPUS);
            }
        }
        if(percept == Percept.WINDY){
            Truth answer = base.ask(RoomType.PIT);
            if(answer == Truth.MAYBE){
                base.tell(Truth.FALSE, RoomType.PIT);
            }
        }
    }

    /**
     * Check to see if a Room is over a certain type by checking it's neighbors information
     * @param x x-position of the Room we are examining
     * @param y y-position of the Room we are examining
     * @param type does that Room match the type given? update accordingly
     */
    public void checkIfTrue(int x, int y, RoomType type){
        // if a room is surrounded by 3 falses, can confirm that square is in fact false

        int numFalse = 0;

        boolean left = true, right = true, up = true, down = true;

        //East neighbor
        if(map[x+1][y].ask(type) == Truth.FALSE){
            numFalse++;
            right = false;
        } //North neighbor
        if(map[x][y+1].ask(type) == Truth.FALSE){
            numFalse++;
            up = false;
        } //West neighbor
        if(map[x-1][y].ask(type) == Truth.FALSE){
            numFalse++;
            left = false;
        } //South neighbor
        if(map[x][y-1].ask(type) == Truth.FALSE){
            numFalse++;
            down = false;
        }

        //if we have 3 false we can make an inference
        //make sure to make WUMPUS false if we assign PIT true, and vice versa
        if(numFalse == 3){
            if(right){
                map[x+1][y].tell(Truth.TRUE, type);
                if(type == RoomType.WUMPUS){
                    map[x+1][y].tell(Truth.FALSE, RoomType.WUMPUS);
                } else {
                    map[x+1][y].tell(Truth.FALSE, RoomType.PIT);
                }
            } else if(up){
                map[x][y+1].tell(Truth.TRUE, type);
                if(type == RoomType.WUMPUS){
                    map[x][y+1].tell(Truth.FALSE, RoomType.WUMPUS);
                } else {
                    map[x][y+1].tell(Truth.FALSE, RoomType.PIT);
                }
            } else if(left){
                map[x-1][y].tell(Truth.TRUE, type);
                if(type == RoomType.WUMPUS){
                    map[x-1][y].tell(Truth.FALSE, RoomType.WUMPUS);
                } else {
                    map[x-1][y].tell(Truth.FALSE, RoomType.PIT);
                }
            } else if(down){
                map[x][y-1].tell(Truth.TRUE, type);
                if(type == RoomType.WUMPUS){
                    map[x][y-1].tell(Truth.FALSE, RoomType.WUMPUS);
                } else {
                    map[x][y-1].tell(Truth.FALSE, RoomType.PIT);
                }
            }
        }
    }

    /**
     * Checks to see if our current Room houses the Gold
     * @param x current x-position of our Room
     * @param y current y-position of our Room
     * @param p current percepts present in this Room
     * @return True/False if Gold is in this Room
     */
    public boolean checkGold(int x, int y, Percept p){
        if(p == Percept.GLITTER){   // we found gold!
            map[x][y].tell(Truth.TRUE, RoomType.GOLD); // update knowledge base that we found gold
            return true;    // this Room has the gold!
        }
        map[x][y].tell(Truth.FALSE, RoomType.GOLD); // sadly this rRoom doesn't have the gold
        return false;   // return false
    }

    /**
     * Check to see if we are safe to move forward in this direction
     * @param x x-coordinate of the current Room
     * @param y y-coordinate of the current Room
     * @param d direction explorer is currently facing
     * @return
     */
    public boolean isSafe(int x, int y, Direction d){
        switch(d){  // switch on our direction
            case EAST:  // if we are looking East into a room without Wumpus or Pit, it is safe to go!
                if(map[x+1][y].ask(RoomType.WUMPUS) == Truth.FALSE && map[x+1][y].ask(RoomType.PIT) == Truth.FALSE){
                    return true;
                }
                break;
            case NORTH: // if we are looking North into a room without Wumpus or Pit, it is safe to go!
                if(map[x][y+1].ask(RoomType.WUMPUS) == Truth.FALSE && map[x][y+1].ask(RoomType.PIT) == Truth.FALSE){
                    return true;
                }
                break;
            case WEST:  // if we are looking West into a room without Wumpus or Pit, it is safe to go!
                if(map[x-1][y].ask(RoomType.WUMPUS) == Truth.FALSE && map[x-1][y].ask(RoomType.PIT) == Truth.FALSE){
                    return true;
                }
                break;
            case SOUTH: // if we are looking South into a room without Wumpus or Pit, it is safe to go!
                if(map[x][y-1].ask(RoomType.WUMPUS) == Truth.FALSE && map[x][y-1].ask(RoomType.PIT) == Truth.FALSE){
                    return true;
                }
                break;
        }
        // unknown case?  Definitely not safe!
        return false;
    }

    /**
     * Return boolean if you can shoot a wumpus or not
     * @param x x-position of this current Room on the map
     * @param y y-position of this current Room on the map
     * @param d the direction the Explorer is currently facing
     * @return true/false
     */
    public boolean canShootWumpus(int x, int y, Direction d){
        int xMod = 0, yMod = 0; // used to modify our position accordinly based on direction

        // set modifiers based on players current direction
        switch(d){
            case EAST:
                xMod = 1;   // +1 to x-position while flying East
                break;
            case NORTH:
                yMod = 1;   // +1 to y-position while flying North
                break;
            case WEST:
                xMod = -1;  // -1 to x-position while flying West
                break;
            case SOUTH:
                yMod = -1;  // -1 to y-position while flying South
                break;
        }

        // while the arrow isn't done flying...
        while(true){
            x = x + xMod; // modify the arrows x-pos
            y = y + yMod; // modify the arrows y-pos
            if(map[x][y].ask(RoomType.WUMPUS) == Truth.TRUE){   // We hit a wumpus!
                tempXWumpus = x;    // this is the dead wumpus location(x)
                tempYWumpus = y;    // this is the dead wumpus location(y)
                return true;    // break out of this loop
            } else if(map[x][y].ask(RoomType.OBSTACLE) == Truth.TRUE || // we will definitely hit an obstacle this way
                    map[x][y].ask(RoomType.OBSTACLE) == Truth.MAYBE || // we might hit an obstacle here or ...
                    map[x][y].ask(RoomType.WUMPUS) == Truth.MAYBE){   // we might hit a wumpus, we might not
                return false; // since this arrow isn't certain, we can't shoot the wumpus
            }
        }
    }

    /**
     * Returns a boolean if you are near a valid unvisited Room that is safe to explor
     * @param x x-coordinate of current Room location in the Map
     * @param y y-coordinate of current Room location in the Mpa
     * @return  true/false
     */
    public boolean nearUnvisited(int x, int y) {
        // check the adjacent squares for possible unvisited and safe Rooms
        return (!map[x][y+1].visited && isSafe(x, y, Direction.NORTH)) ||
                (!map[x+1][y].visited && isSafe(x, y, Direction.EAST)) ||
                (!map[x][y-1].visited && isSafe(x, y, Direction.SOUTH)) ||
                (!map[x-1][y].visited && isSafe(x, y, Direction.WEST));
    }

    /**
     * Update possible Wumpus state to dead and move accordingly
     */
    public void inferDeadWumpus(){
        // used temporary stored x, y information to update Wumpus death
        map[tempXWumpus][tempYWumpus].tell(Truth.FALSE, RoomType.WUMPUS);
        tempXWumpus = 0;
        tempYWumpus = 0;
    }
}
