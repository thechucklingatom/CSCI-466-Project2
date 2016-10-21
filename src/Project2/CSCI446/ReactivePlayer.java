package Project2.CSCI446;

import java.util.Random;

import Exceptions.OutOfArrowsException;

/**
 * Created by thechucklingatom on 10/18/2016.
 */
public class ReactivePlayer extends Player{
	Random random;

	ReactivePlayer(Room currentRoom, World ourWorld, int numberOfArrows){
		this.currentRoom = currentRoom;
		world = ourWorld;
		arrowCount = numberOfArrows;
		totalCost = 0;
		random = new Random();
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
				turn();
			}else if(roomMovingTo == RoomType.WUMPUS){
				totalCost -= 1000;
				deaths.add(roomMovingTo);
				try {
					shoot();
				}catch (OutOfArrowsException ex){
					turn();
				}
			}else if(roomMovingTo == RoomType.PIT){
				totalCost -= 1000;
				deaths.add(roomMovingTo);
				turn();
			}else{
				totalCost -= 1;
				if(random.nextBoolean()){
					turn();
				}else{
					currentRoom = world.move(direction);
				}
			}
		}
	}

	@Override
	public Percept shoot() throws OutOfArrowsException {
		return world.shoot(direction);
	}

	public void turn(){

		if(random.nextBoolean()){
			turnRight();
		}else{
			turnLeft();
		}
	}
}
