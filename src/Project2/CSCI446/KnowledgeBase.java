package Project2.CSCI446;

/**
 * Created by Alan Fraticelli on 10/20/2016.
 */
public class KnowledgeBase {

    Truth wumpus = Truth.MAYBE;
    Truth pit = Truth.MAYBE;
    Truth gold = Truth.MAYBE;
    Truth obstacle = Truth.MAYBE;
    Truth empty = Truth.MAYBE;

    public KnowledgeBase(){

    }
    public Truth ask(RoomType p){
        return Truth.F;
    }

    public void tell(Truth inTruth, RoomType roomtype){

    }
}
