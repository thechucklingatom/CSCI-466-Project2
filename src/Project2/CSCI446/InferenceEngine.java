package Project2.CSCI446;

/**
 * Created by Alan Fraticelli on 10/20/2016.
 */
public class InferenceEngine {
    KnowledgeBase[][] map;

    public InferenceEngine(KnowledgeBase[][] inMap){
        map = inMap;
    }

    public void update(KnowledgeBase base){
        //if breeze/stink are false:
        //swtich to set predicate that is related to percept
        //call ask(p)
        //if return is MAYBE, tell(F, p)
    }

    public void checkIfTrue(RoomType p){
        //ask(p) the squares around this one for p
        //if 3 are false
        //tell(T, p) on the 1 of
    }
}
