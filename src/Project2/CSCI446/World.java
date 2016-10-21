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

	//moves to the next room based on what direction you are going.
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

	//this checks to see if you can move to the next room. If you die it will tell you and
	//you handle that in your player. It will also tell you if you bump. Returns the type
	//of the room for the player to handle
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
			if(xLocation + 1 == world.length){
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
			if(yLocation + 1 == world.length){
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

	//handles all the shoot logic, checks the direction. Shoots the arrow until it hits a wall
	//or obstacle. If it does it returns SILENCE, otherwise returns SCREAM.
	public Percept shoot(Direction direction){

		if(direction == Direction.EAST){
			for(int iter = xLocation; iter >= 0; iter--){
				if(world[iter][yLocation].getType() == RoomType.OBSTACLE){
					return Percept.SILENCE;
				}else if(world[iter][yLocation].getType() == RoomType.WUMPUS){
					removeStenchAroundRoom(iter, yLocation);
					world[iter][yLocation].setType(RoomType.EMPTY);
					return Percept.SCREAM;
				}
			}
		}else if(direction == Direction.WEST){
			for(int iter = xLocation; iter < world.length; iter++){
				if(world[iter][yLocation].getType() == RoomType.OBSTACLE){
					return Percept.SILENCE;
				}else if(world[iter][yLocation].getType() == RoomType.WUMPUS){
					removeStenchAroundRoom(iter, yLocation);
					world[iter][yLocation].setType(RoomType.EMPTY);
					return Percept.SCREAM;
				}
			}
		}else if(direction == Direction.NORTH){
			for(int iter = yLocation; iter >= 0; iter--){
				if(world[xLocation][iter].getType() == RoomType.OBSTACLE){
					return Percept.SILENCE;
				}else if(world[xLocation][iter].getType() == RoomType.WUMPUS){
					removeStenchAroundRoom(xLocation, iter);
					world[xLocation][iter].setType(RoomType.EMPTY);
					return Percept.SCREAM;
				}
			}
		}else if(direction == Direction.SOUTH){
			for(int iter = yLocation; iter < world.length; iter++){
				if(world[xLocation][iter].getType() == RoomType.OBSTACLE){
					return Percept.SILENCE;
				}else if(world[xLocation][iter].getType() == RoomType.WUMPUS){
					removeStenchAroundRoom(xLocation, iter);
					world[xLocation][iter].setType(RoomType.EMPTY);
					return Percept.SCREAM;
				}
			}
		}

		return Percept.SILENCE;
	}

	//gets rid of the stench on the surrounding rooms so that the reasoning will know there is no wumpus
	public void removeStenchAroundRoom(int xLocation, int yLocation){
		if(xLocation != 0){
			world[xLocation -1][yLocation].delPercept(Percept.SMELLY);
		}

		if(xLocation < world.length - 1){
			world[xLocation + 1][yLocation].delPercept(Percept.SMELLY);
		}

		if(yLocation != 0){
			world[xLocation][yLocation - 1].delPercept(Percept.SMELLY);
		}

		if(yLocation < world.length - 1){
			world[xLocation][yLocation + 1].delPercept(Percept.SMELLY);
		}
	}

	//returns the currentRoom
	public Room currentRoom(){
		return world[xLocation][yLocation];
	}

	//counts the number of whatevers
	public int numberOfWumpi(){
		int numberOfWumpuses = 0;
		for (Room[] roomArray: world) {
			for (Room room : roomArray) {
				if(RoomType.WUMPUS == room.getType()){
					numberOfWumpuses++;
				}
			}
		}
		return numberOfWumpuses;
	}
}
