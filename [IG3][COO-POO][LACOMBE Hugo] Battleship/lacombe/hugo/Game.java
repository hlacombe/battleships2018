package lacombe.hugo;

public class Game {
	private Player p1;
	private Player p2;
	private int turnCount ;

	public Game(int[] parameters) {
		int mode = parameters[0];
		int difficulty1 = parameters[1];
		int difficulty2 = parameters[2];
		this.turnCount=0;
		switch(mode) {
		case 1 : {
			p1=new Player();
			p2=new Player();
			break;
		}
		case 2 :{
			p1=new Player();
			p2=new Computer(difficulty1);
			break;
		}
		case 3 :{
			p1=new Computer(difficulty1);
			p2=new Computer(difficulty2);
			break;
		}
		}
	}
	//getters and setters
	public Player getP1() {
		return p1;
	}
	public Player getP2() {
		return p2;
	}
	public Player getCurrentPlayer() {
		if(turnCount %2 == 0) {
			return p1;
		}
		else return p2;
	}
	//methods
	public String getCurrentPlayerName() {
		String toReturn = "p1";
		if(getCurrentPlayer()==p2) {
			toReturn = "p2";
		}
		return toReturn;
	}

	public int getTurnCount() {
		return turnCount;
	}
	public void upTurnCount() {
		this.turnCount++;
	}
	//main


	//methods

	public String guess() {
		//method make a valid guess & update grids from both players
		Coordinates guess = new Coordinates();
		boolean result;

		guess = this.getCurrentPlayer().makeGuess(this.opponent());
		result = this.opponent().checkGuess(guess);
		
		int aboutSunk = -1;
		Ship shipHit=null;
		
		String toReturn;
		if(result) {//si hit
			shipHit = opponent().getShip(guess);
			aboutSunk = shipHit.isSunkThisTurn();
			toReturn = this.getCurrentPlayerName()+" the "+shipHit.name();
			switch(aboutSunk) {
			case 0 : toReturn = toReturn +" is already down!"; break;
			case 1 : toReturn = toReturn +" is down!"; break;
			case -1 : toReturn = toReturn +" is hit!"; break;
			default: break;
			}
		}
		else {
			toReturn = "Missed!";
		}
		this.getCurrentPlayer().updatePlayerActive(guess, result,aboutSunk, shipHit);
		this.opponent().updatePlayerPassive(guess, result);
		
		return toReturn;
	}



	public boolean endGame() {
		return this.getCurrentPlayer().hasLost() || this.opponent().hasLost();
	}

	public Player winner() {
		if(this.getCurrentPlayer().hasLost()) {
			return this.opponent();
		}
		else {
			return this.getCurrentPlayer();
		}
	}

	public Player opponent() {
		if(this.getCurrentPlayer()==this.getP1()) {
			return p2;
		}
		else {
			return p1;
		}
	}
	public String nomPlayer(Player p) {
		if(p==p1) {
			return "P1";
		}
		else {return "P2";
		}
	}

}
