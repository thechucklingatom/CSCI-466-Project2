package Project2.CSCI446;

import Exceptions.OutOfArrowsException;

/**
 * Created by thechucklingatom on 10/18/2016.
 */
public class ReactivePlayer extends Player{

	ReactivePlayer(Room currentRoom, World ourWorld, int numberOfArrows){
		this.currentRoom = currentRoom;
		world = ourWorld;
		arrowCount = numberOfArrows;
		totalCost = 0;
	}

	@Override
	public void solve() {

	}

	@Override
	public void shoot() throws OutOfArrowsException {

	}
}
