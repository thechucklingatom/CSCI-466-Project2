package Project2.CSCI446;

/**
 * Created by Alan Fraticelli on 10/20/2016.
 */
public class InferenceEngine {
    KnowledgeBase[][] map;

    public InferenceEngine(KnowledgeBase[][] inMap){
        map = inMap;
    }

    //we are looking at an adjacent square when we call this method
    public void update(KnowledgeBase base, Percept percept){
        //if breeze/stink are false:
            //get predicate that is related to percept
            //call ask(p)
            //if return is MAYBE, tell(F, p)
        if(percept != Percept.SMELLY){
            Truth answer = base.ask(RoomType.WUMPUS);
            if(answer == Truth.MAYBE){
                base.tell(answer, RoomType.WUMPUS);
            }
        } if(percept != Percept.WINDY){
            Truth answer = base.ask(RoomType.PIT);
            if(answer == Truth.MAYBE){
                base.tell(answer, RoomType.PIT);
            }
        }

    }

    public void checkIfTrue(Room p, RoomType sense){
        //ask(p) the squares around this one for predicate pre
        //if 3 are false
        //tell(T, p) on the 1 of

        // get x, y position of this room
        int x = p.getXPosition();
        int y = p.getYPosition();

        boolean left = false, right = false, up = false, down = false;

        if (x > 0) { // we can check our left neighbor
            //if (map[x-1][y].)
        }
    }

    public boolean canShootWumpus(int x, int y, Direction d){
        //iterate through the squares infront of the agent
            //if wumpus, return true
            //if obstacle, return false
        return false;
    }
}
