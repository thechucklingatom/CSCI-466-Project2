package Project2.CSCI446;

import java.util.ArrayList;
import java.util.List;

import Exceptions.OutOfArrowsException;

/**
 * Created by thechucklingatom on 10/16/16.
 */
public abstract class Player {

	public int arrowCount;
	//Win +1000, die -1000, move/turn -1, fire arrow -10, kill wumpus +10
	public int totalCost;
	public List<RoomType> deaths = new ArrayList<RoomType>();
	public Direction direction = Direction.EAST;
	public Room currentRoom;
	public World world;

	public void turnLeft(){
		totalCost -= 1;
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

	public abstract void solve();

	public abstract Percept shoot() throws OutOfArrowsException;

	public void turnRight(){
		totalCost -= 1;
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

	public List<Percept> getPercepts(){
		return currentRoom.getPercepts();
	}


}
