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
        logic = new InferenceEngine();
        arrowCount = numArrows;
        currentRoom = inRoom;
        world = theWorld;
        map = new KnowledgeBase[625][625];
        for (KnowledgeBase[] x : map) {
            for(KnowledgeBase y : x) {
                y = new KnowledgeBase();
            }
        }
    }

    //when we move, make sure to update currentRoom
    @Override
    public void solve() {
        //while loop that continues until GOAL
            //method calls to do the logic
            //get percepts

    }

    @Override
    public void shoot() throws OutOfArrowsException {

    }
}
