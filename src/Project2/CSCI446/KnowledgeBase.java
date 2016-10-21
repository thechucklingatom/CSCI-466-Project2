package Project2.CSCI446;

/**
 * Created by Alan Fraticelli on 10/20/2016.
 */
public class KnowledgeBase {

    private Truth wumpus = Truth.MAYBE;
    private Truth pit = Truth.MAYBE;
    private Truth gold = Truth.MAYBE;
    private Truth obstacle = Truth.MAYBE;
    private Truth empty = Truth.MAYBE;
    protected boolean visited = false;


    public KnowledgeBase(){

    }
    public Truth ask(RoomType p){
        return Truth.F;
    }

    public void tell(Truth inTruth, RoomType roomtype){

    }
}
