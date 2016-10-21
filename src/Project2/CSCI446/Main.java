package Project2.CSCI446;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * @author thechucklingatom
 */
public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// write your code here

		int worldSize = 5;
		double wumpusProb = .1, obstacleProb = .1, pitProb = .1;

		PrintWriter writer = new PrintWriter("tests.csv");

		String header = "Player Type,Total Score,World Size,Wumpus Probability,Obstacle "
				+ "Probability,Pit Probability,Total Deaths,Pit Deaths,Wumpus Deaths,"
				+ "Have gold";

		writer.println(header);

		Generator generator = new Generator(worldSize, pitProb, wumpusProb, obstacleProb);

		World gameWorld = new World(generator.getWorld(), generator.startXPosition(),
				generator.startYPosition());

		Player player = new ReactivePlayer(gameWorld.currentRoom(), gameWorld, gameWorld.numberOfWumpi());

		player.solve();

		int wumpusDeaths = 0;
		int pitDeaths = 0;

		for (RoomType roomType :
				player.deaths) {
			if (roomType == RoomType.WUMPUS) {
				wumpusDeaths++;
			} else if (roomType == RoomType.PIT) {
				pitDeaths++;
			}
		}

		String toWrite = String.format("%s,%d,%d,%f,%f,%f,%d,%d,%d,%b", player.getClass().getSimpleName(),
				player.totalCost, worldSize, wumpusProb, obstacleProb, pitProb, player.deaths.size(),
				pitDeaths, wumpusDeaths, player.haveGold);

		writer.println(toWrite);

		writer.close();
	}
}
