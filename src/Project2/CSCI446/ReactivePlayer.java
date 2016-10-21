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
		RoomType roomMovingTo;
		while(totalCost > -10000) {

			if(currentRoom.getPercepts().contains(Percept.GLITTER)){
				pickUpGold();
				return;
			}

			roomMovingTo = world.canMove(Direction.EAST);

			if(roomMovingTo == RoomType.OBSTACLE){
				totalCost -= 1;
				turnRight();
			}else if(roomMovingTo == RoomType.WUMPUS){
				totalCost -= 1000;
				deaths.add(roomMovingTo);
				try {
					shoot();
				}catch (OutOfArrowsException ex){
					turnRight();
				}
			}else if(roomMovingTo == RoomType.PIT){
				totalCost -= 1000;
				deaths.add(roomMovingTo);
				turnRight();
			}else{
				totalCost -= 1;
				currentRoom = world.move(direction);
			}
		}

	}

	@Override
	public Percept shoot() throws OutOfArrowsException {
		return world.shoot(direction);
	}
}
