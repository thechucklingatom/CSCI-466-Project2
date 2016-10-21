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

	//solve the game, maybe
	@Override
	public void solve() {
		RoomType roomMovingTo;

		//run for a max cost of 100000, since it cannot always solve it.
		while(totalCost > -100000) {

			//if in the room get the gold
			if(currentRoom.getPercepts().contains(Percept.GLITTER)){
				pickUpGold();
				return;
			}

			//check where you can go
			roomMovingTo = world.canMove(direction);

			//if obstacle (or wall) you have to turn and try somewhere else
			if(roomMovingTo == RoomType.OBSTACLE){
				totalCost -= 1;
				turn();
			}else if(roomMovingTo == RoomType.WUMPUS){
				totalCost -= 1000;
				//since you moved into a wumpus you die
				deaths.add(roomMovingTo);
				try {
					//shoot if you can
					shoot();
				}catch (OutOfArrowsException ex){
					//if you cant try to go around
					turn();
				}
			}else if(roomMovingTo == RoomType.PIT){
				totalCost -= 1000;
				//moved into a pit so you dead again.
				deaths.add(roomMovingTo);
				//try to go around
				turn();
			}else{
				//pick a random action
				if(random.nextBoolean()){
					turn();
				}else{
					totalCost -= 1;
					currentRoom = world.move(direction);
				}
			}
		}
	}

	//call the world shoot function
	@Override
	public Percept shoot() throws OutOfArrowsException {
		return world.shoot(direction);
	}

	//turn a random direction
	public void turn(){

		if(random.nextBoolean()){
			turnRight();
		}else{
			turnLeft();
		}
	}
}
