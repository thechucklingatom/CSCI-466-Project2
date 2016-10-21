package Project2.CSCI446;

/**
 * Created by Alan Fraticelli on 10/20/2016.
 */
public class KnowledgeBase {

    private Truth wumpus = Truth.FALSE; // assume a Room isn't wumpus since no smell yet
    private Truth pit = Truth.FALSE;    // assume a Room isn't btmls pit since no breeze yet
    private Truth gold = Truth.FALSE; // assume a Room isn't gold since sinse in room only
    private Truth obstacle = Truth.FALSE;   // assume a Room isn't obstacle since no bump yet
    private Truth empty = Truth.MAYBE;  // might be empty since can't sense gold/obstacle outside square
    protected boolean visited = false; // this room is unknown to us currently


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
        } else { // infer empty room question
            return empty;
        }
    }

    public void tell(Truth inTruth, RoomType roomtype){
        if (RoomType.GOLD == roomtype) {
            gold = Truth.TRUE;  // we found gold!
            wumpus = Truth.FALSE;
            obstacle = Truth.FALSE;
            pit = Truth.FALSE;
            empty = Truth.FALSE;
        } else if(RoomType.WUMPUS == roomtype) {
            gold = Truth.FALSE;
            wumpus = Truth.TRUE; // we hit a wumpus
            obstacle = Truth.FALSE;
            pit = Truth.FALSE;
            empty = Truth.FALSE;
        } else if(RoomType.OBSTACLE == roomtype) {
            gold = Truth.FALSE;
            wumpus = Truth.FALSE;
            obstacle = Truth.TRUE;  // bumped an obstacle
            pit = Truth.FALSE;
            empty = Truth.FALSE;
        } else if (RoomType.PIT == roomtype) {
            gold = Truth.FALSE;
            wumpus = Truth.FALSE;
            obstacle = Truth.FALSE;
            pit = Truth.TRUE;   // fell into a pit
            empty = Truth.FALSE;
        } else {
            gold = Truth.FALSE;
            wumpus = Truth.FALSE;
            obstacle = Truth.FALSE;
            pit = Truth.TRUE;
        }
    }
}
