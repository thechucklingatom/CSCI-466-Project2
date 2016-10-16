package Project2.CSCI446;

import Exceptions.OutOfArrowsException;

/**
 * Created by thechucklingatom on 10/16/16.
 */
public abstract class Player {

	public int arrowCount;
	public Direction direction = Direction.EAST;
	public Room currentRoom;

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

	public void shoot() throws OutOfArrowsException{
		if(arrowCount == 0){
			System.out.println("Out of error cannot shoot!");
			throw new OutOfArrowsException();
		}else{
			//TODO shoot arrow logic here
			arrowCount--;
		}
	}

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
