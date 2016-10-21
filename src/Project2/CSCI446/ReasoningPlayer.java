package Project2.CSCI446;

import Exceptions.OutOfArrowsException;

import java.util.List;

/**
 * Created by Alan Fraticelli on 10/20/2016.
 */
public class ReasoningPlayer extends Player{
    private InferenceEngine logic;
    private KnowledgeBase[][] map;
    private List<Percept> currentPercept;

    public ReasoningPlayer(int numArrows, Room inRoom, World theWorld){
        arrowCount = numArrows;
        currentRoom = inRoom;
        world = theWorld;
        map = new KnowledgeBase[50][50];
        for (KnowledgeBase[] x : map) {
            for(KnowledgeBase y : x) {
                y = new KnowledgeBase();
            }
        }
        logic = new InferenceEngine(map);
    }

    //when we move, make sure to update currentRoom
    @Override
    public void solve() {
        //while loop that continues until GOAL
            //CHECK FOR GOAL
            //get percepts
            //update the predicates of the squares around us
                //for each adjacent, call update(map[][])
            //iterate percepts to see if we can set squares to true
            //variables if we have stink/breeze percepts
                //call checkIfTrue(curPer)
        //---WE ARE NOW INFERING MOVE---
            //check if we can shoot a wumpus
                //if true, then shoot
            //start at the right square, check for visited
                //if not, turn right
                //if so, try forward, then left
                //if all these fail, spiral outwards until you hit non-visted square
                //REVISIT THIS IDEA FOR PATHFINDING
    }

    @Override
    public Percept shoot() throws OutOfArrowsException {
        return null;
    }
}
