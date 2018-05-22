package lacombe.hugo;

import java.util.Scanner;

public class Battleship {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

		boolean wantToPlay = true; 
		//int mode[] = ToolBox.getGameParameters();
		while(wantToPlay) {
			int mode[] = {2,3,1};
			Game game = new Game(mode);
			//each player initialize ships
			System.out.println("########################"+game.getCurrentPlayerName() + " Place your fleet########################");
			System.out.println("To place a ship, you will need to type in a starting coordinate, then  the ending coordinates" );
			game.getP1().placeFleet();
			System.out.println("########################"+game.nomPlayer(game.opponent()) + " Place your fleet########################");
			System.out.println("To place a ship, you will need to type in a starting coordinate, then  the ending coordinates" );
			game.getP2().placeFleet();

			while(!(game.endGame())) {
				System.out.println(game.getTurnCount());
				System.out.println("########################"+game.getCurrentPlayerName() + " Turn"+game.getTurnCount()+"########################");
				//game.getCurrentPlayer().print(game.opponent());
				System.out.println(game.getCurrentPlayerName() + ", you need to make a guess");
				String toPrint = game.guess();
				System.out.println(toPrint);
				game.upTurnCount();
			}
			System.out.println("Player "+game.nomPlayer(game.winner())+" wins in "+(game.getTurnCount()/2)+"turn");
			System.out.println("Want to play again? y/n");
			wantToPlay = ToolBox.wantToContinue();
			int tmp=mode[1];
			mode[1]=mode[2];
			mode[2]=tmp;
		}
		sc.close();
	}
}
