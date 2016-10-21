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

    public void checkIfTrue(int x, int y, RoomType type){
        //ask(type) the squares around this one for predicate pre
        //if 3 are false
        //tell(T, type) on the 1 of

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
                    map[x+1][y].tell(Truth.FALSE, RoomType.PIT);
                } else {
                    map[x+1][y].tell(Truth.FALSE, RoomType.WUMPUS);
                }
            } else if(up){
                map[x][y+1].tell(Truth.TRUE, type);
                if(type == RoomType.WUMPUS){
                    map[x][y+1].tell(Truth.FALSE, RoomType.PIT);
                } else {
                    map[x][y+1].tell(Truth.FALSE, RoomType.WUMPUS);
                }
            } else if(left){
                map[x-1][y].tell(Truth.TRUE, type);
                if(type == RoomType.WUMPUS){
                    map[x-1][y].tell(Truth.FALSE, RoomType.PIT);
                } else {
                    map[x-1][y].tell(Truth.FALSE, RoomType.WUMPUS);
                }
            } else if(down){
                map[x][y-1].tell(Truth.TRUE, type);
                if(type == RoomType.WUMPUS){
                    map[x][y-1].tell(Truth.FALSE, RoomType.PIT);
                } else {
                    map[x][y-1].tell(Truth.FALSE, RoomType.WUMPUS);
                }
            }
        }
    }

    public boolean checkGold(int x, int y, Percept p){
        if(p == Percept.GLITTER){
            map[x][y].tell(Truth.TRUE, RoomType.GOLD);
            return true;
        }
        map[x][y].tell(Truth.FALSE, RoomType.GOLD);
        return false;
    }

    public boolean isSafe(int x, int y, Direction d){
        switch(d){
            case EAST:
                if(map[x+1][y].ask(RoomType.WUMPUS) == Truth.FALSE && map[x+1][y].ask(RoomType.PIT) == Truth.FALSE){
                    return true;
                }
                break;
            case NORTH:
                if(map[x][y+1].ask(RoomType.WUMPUS) == Truth.FALSE && map[x][y+1].ask(RoomType.PIT) == Truth.FALSE){
                    return true;
                }
                break;
            case WEST:
                if(map[x-1][y].ask(RoomType.WUMPUS) == Truth.FALSE && map[x-1][y].ask(RoomType.PIT) == Truth.FALSE){
                    return true;
                }
                break;
            case SOUTH:
                if(map[x][y-1].ask(RoomType.WUMPUS) == Truth.FALSE && map[x][y-1].ask(RoomType.PIT) == Truth.FALSE){
                    return true;
                }
                break;
        }
        return false;
    }

    public boolean canShootWumpus(int x, int y, Direction d){
        //iterate through the squares infront of the agent
            //if wumpus, return true
            //if obstacle, return false

        boolean done = false;
        int xMod = 0, yMod = 0;

        switch(d){
            case EAST:
                xMod = 1;
                break;
            case NORTH:
                yMod = 1;
                break;
            case WEST:
                xMod = -1;
                break;
            case SOUTH:
                yMod = -1;
                break;
        }

        while(!done){
            x = x + xMod;
            y = y + yMod;
            if(map[x][y].ask(RoomType.WUMPUS) == Truth.TRUE){
                tempXWumpus = x;
                tempYWumpus = y;
                return true;
            } else if(map[x][y].ask(RoomType.OBSTACLE) == Truth.FALSE ||
                      map[x][y].ask(RoomType.OBSTACLE) == Truth.MAYBE ||
                      map[x][y].ask(RoomType.WUMPUS) == Truth.MAYBE){
                return false;
            }
        }

        return false;
    }

    public void inferDeadWumpus(){
        map[tempXWumpus][tempYWumpus].tell(Truth.FALSE, RoomType.WUMPUS);
        tempXWumpus = 0;
        tempYWumpus = 0;
    }
}
