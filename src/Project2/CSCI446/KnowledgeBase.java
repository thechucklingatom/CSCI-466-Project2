package Project2.CSCI446;

/**
 * Created by Alan Fraticelli on 10/20/2016.
 */
public class KnowledgeBase {

    private Truth wumpus = Truth.MAYBE; // assume a Room isn't wumpus since no smell yet
    private Truth pit = Truth.MAYBE;    // assume a Room isn't btmls pit since no breeze yet
    private Truth gold = Truth.MAYBE; //
    private Truth obstacle = Truth.MAYBE;
    private Truth empty = Truth.MAYBE;
    protected boolean visited = false;
    public int timesVisted = 0;

    public KnowledgeBase(){

    }
    public Truth ask(RoomType p){
        if (RoomType.GOLD == p) {
            return gold;
        } else if (RoomType.WUMPUS == p) {
            return wumpus;
        } else if (RoomType.PIT == p) {
            return pit;
        } else if (RoomType.OBSTACLE == p) {
            return obstacle;
        } else if (RoomType.EMPTY == p) {
            return empty;
        }
        return null;
    }

    public void tell(Truth inTruth, RoomType roomtype){
        switch(roomtype){
            case WUMPUS:
                wumpus = inTruth;
                break;
            case PIT:
                pit = inTruth;
                break;
            case GOLD:
                gold = inTruth;
                break;
            case OBSTACLE:
                obstacle = inTruth;
                break;
            case EMPTY:
                empty = inTruth;
                break;
            default:
                break;
        }
    }
}
