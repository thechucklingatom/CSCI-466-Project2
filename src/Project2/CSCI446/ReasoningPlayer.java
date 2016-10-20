package Project2.CSCI446;

import Exceptions.OutOfArrowsException;

/**
 * Created by Alan Fraticelli on 10/20/2016.
 */
public class ReasoningPlayer extends Player{
    private InferenceEngine logic;
    int arrowCount;

    public ReasoningPlayer(){
        logic = new InferenceEngine();
    }

    @Override
    public void shoot() throws OutOfArrowsException {

    }
}
