package Project2.CSCI446;

/**
 * Created by thechucklingatom on 10/18/2016.
 */
public class World {
	Room[][] world;
	int xLocation;
	int yLocation;

	public World(Room[][] world, int xLocation, int yLocation){
		this.world = world;
		this.xLocation = xLocation;
		this.yLocation = yLocation;
	}

	public Room move(Direction direction){
		if(direction == Direction.EAST){
			xLocation--;
		}else if(direction == Direction.WEST){
			xLocation++;
		}else if(direction == Direction.NORTH){
			yLocation--;
		}else if(direction == Direction.SOUTH){
			yLocation++;
		}

		return world[xLocation][yLocation];
	}

	public boolean isBump(Direction direction){
		if(direction == Direction.EAST){
			if(xLocation - 1 < 0 || world[xLocation][yLocation].getType() == RoomType.OBSTACLE){
				return true;
			}
		}else if(direction == Direction.WEST){
			if(xLocation + 1 >= world.length
					|| world[xLocation][yLocation].getType() == RoomType.OBSTACLE){
				return true;
			}
		}else if(direction == Direction.NORTH){
			if(yLocation - 1 < 0 || world[xLocation][yLocation].getType() == RoomType.OBSTACLE){
				return true;
			}
		}else if(direction == Direction.SOUTH){
			if(yLocation + 1 >= world.length
					|| world[xLocation][yLocation].getType() == RoomType.OBSTACLE){
				return true;
			}
		}

		return false;
	}
}
