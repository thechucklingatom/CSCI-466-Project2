package Project2.CSCI446;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * @author thechucklingatom
 */
public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		// write your code here

		Random random = new Random();

		//change this value to generate different world sizes
		int worldSize = 5;


		//change this to have a different file name.
		PrintWriter writer = new PrintWriter("testsReasoning" + worldSize + ".csv");

		//header for the csv file
		String header = "Player Type,Total Score,World Size,Wumpus Probability,Obstacle "
				+ "Probability,Pit Probability,Total Deaths,Pit Deaths,Wumpus Deaths,"
				+ "Have gold,Have gold int";

		writer.println(header);

		//run 1000 tests to see how they work, and the averages
		for(int i = 0; i < 1; i++) {
			//random probability for each world
			double wumpusProb = random.nextDouble(), obstacleProb = random.nextDouble(),
					pitProb = random.nextDouble();

			//new generator
			Generator generator = new Generator(worldSize, pitProb, wumpusProb, obstacleProb);

			//get the world to solve
			World gameWorld = new World(generator.getWorld(), generator.startXPosition(),
					generator.startYPosition());

			//change this to use the different players.
			Player player = new ReasoningPlayer(gameWorld.numberOfWumpi(), gameWorld.currentRoom(), gameWorld );

			//solve the game hopefully
			player.solve();

			//ints to keep track of death count
			int wumpusDeaths = 0;
			int pitDeaths = 0;

			//for each of the deaths count how many
			for (RoomType roomType :
					player.deaths) {
				if (roomType == RoomType.WUMPUS) {
					wumpusDeaths++;
				} else if (roomType == RoomType.PIT) {
					pitDeaths++;
				}
			}

			//format the row for the csv file
			String toWrite = String.format("%s,%d,%d,%f,%f,%f,%d,%d,%d,%b,%d", player.getClass().getSimpleName(),
					player.totalCost, worldSize, wumpusProb, obstacleProb, pitProb, player.deaths.size(),
					pitDeaths, wumpusDeaths, player.haveGold, player.haveGold ? 1 : 0);

			//print to the file
			writer.println(toWrite);
		}

		//close the buffer so the file is written
		writer.close();
	}
}
