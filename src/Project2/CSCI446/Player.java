package Project2.CSCI446;

import Exceptions.OutOfArrowsException;

/**
 * Created by thechucklingatom on 10/16/16.
 */
public abstract class Player {

	public int arrowCount;
	//Win +1000, die -1000, move/turn -1, fire arrow -10, kill wumpus +10
	public int totalCost;
	public Direction direction = Direction.EAST;
	public Room currentRoom;
	public World world;

	public void turnLeft(){
		switch(direction){
			case EAST:
				direction = Direction.NORTH;
				break;
			case NORTH:
				direction = Direction.WEST;
				break;
			case WEST:
				direction = Direction.SOUTH;
				break;
			case SOUTH:
				direction = Direction.EAST;
				break;
		}
	}

	public abstract void shoot() throws OutOfArrowsException;

	public void turnRight(){
		switch(direction){
			case EAST:
				direction = Direction.SOUTH;
				break;
			case NORTH:
				direction = Direction.EAST;
				break;
			case WEST:
				direction = Direction.NORTH;
				break;
			case SOUTH:
				direction = Direction.WEST;
				break;
		}
	}


}
