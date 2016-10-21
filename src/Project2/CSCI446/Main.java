package Project2.CSCI446;

/**
 * @author thechucklingatom
 */
public class Main {

	public static void main(String[] args) {
		// write your code here

		Generator generator = new Generator(5, .1, .1, .1);

		World gameWorld = new World(generator.getWorld(), generator.startXPosition(),
				generator.startYPosition());

		Player player = new ReactivePlayer(gameWorld.currentRoom(), gameWorld, gameWorld.numberOfWumpi());

		player.solve();

		System.out.println(player.totalCost);
		for (RoomType death :
				player.deaths) {
			System.out.println(death);
		}
	}
}
