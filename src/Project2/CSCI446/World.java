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

	public RoomType canMove(Direction direction) {
		if (direction == Direction.EAST) {
			if(xLocation - 1 < 0){
				return RoomType.OBSTACLE;
			}
			if (world[xLocation - 1][yLocation].getType() == RoomType.OBSTACLE
					|| world[xLocation - 1][yLocation].getType() == RoomType.WUMPUS
					|| world[xLocation - 1][yLocation].getType() == RoomType.PIT) {
				return world[xLocation - 1][yLocation].getType();
			}
		} else if (direction == Direction.WEST) {
			if(xLocation + 1 < 0){
				return RoomType.OBSTACLE;
			}
			if (world[xLocation + 1][yLocation].getType() == RoomType.OBSTACLE
					|| world[xLocation + 1][yLocation].getType() == RoomType.WUMPUS
					|| world[xLocation + 1][yLocation].getType() == RoomType.PIT) {
				return world[xLocation + 1][yLocation].getType();
			}
		} else if (direction == Direction.NORTH) {
			if(yLocation - 1 < 0){
				return RoomType.OBSTACLE;
			}
			if (world[xLocation][yLocation - 1].getType() == RoomType.OBSTACLE
					|| world[xLocation][yLocation - 1].getType() == RoomType.WUMPUS
					|| world[xLocation][yLocation - 1].getType() == RoomType.PIT) {
				return world[xLocation][yLocation - 1].getType();
			}
		} else if (direction == Direction.SOUTH) {
			if(yLocation + 1 < 0){
				return RoomType.OBSTACLE;
			}
			if (world[xLocation][yLocation + 1].getType() == RoomType.OBSTACLE
					|| world[xLocation][yLocation + 1].getType() == RoomType.WUMPUS
					|| world[xLocation][yLocation + 1].getType() == RoomType.PIT) {
				return world[xLocation][yLocation + 1].getType();
			}
		}

		return null;
	}

	public Percept shoot(Direction direction){

		if(direction == Direction.EAST){
			for(int iter = xLocation; iter >= 0; iter--){
				if(world[iter][yLocation].getType() == RoomType.OBSTACLE){
					return Percept.SILENCE;
				}else if(world[iter][yLocation].getType() == RoomType.WUMPUS){
					return Percept.SCREAM;
				}
			}
		}else if(direction == Direction.WEST){
			for(int iter = xLocation; iter <= world.length; iter++){
				if(world[iter][yLocation].getType() == RoomType.OBSTACLE){
					return Percept.SILENCE;
				}else if(world[iter][yLocation].getType() == RoomType.WUMPUS){
					return Percept.SCREAM;
				}
			}
		}else if(direction == Direction.NORTH){
			for(int iter = yLocation; iter >= 0; iter--){
				if(world[xLocation][iter].getType() == RoomType.OBSTACLE){
					return Percept.SILENCE;
				}else if(world[xLocation][iter].getType() == RoomType.WUMPUS){
					return Percept.SCREAM;
				}
			}
		}else if(direction == Direction.SOUTH){
			for(int iter = yLocation; iter <= world.length; iter++){
				if(world[xLocation][iter].getType() == RoomType.OBSTACLE){
					return Percept.SILENCE;
				}else if(world[xLocation][iter].getType() == RoomType.WUMPUS){
					return Percept.SCREAM;
				}
			}
		}

		return Percept.SILENCE;
	}
}
