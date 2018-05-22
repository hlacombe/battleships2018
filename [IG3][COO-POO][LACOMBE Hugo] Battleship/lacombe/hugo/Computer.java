package lacombe.hugo;
import java.util.ArrayList;

public class Computer extends Player {

	public static final int maxRow = 10;
	public static final int maxCol = 10;

	private int difficulty;
	private ArrayList<Coordinates> priorityShoot ;

	//getters and setters
	public ArrayList<Coordinates> getPriorityShoot() {
		return priorityShoot;
	}

	public void setPriorityShoot(ArrayList<Coordinates> priorityShoot) {
		this.priorityShoot = priorityShoot;
	}

	public Computer(int difficulty) {
		super();
		this.difficulty = difficulty;
		priorityShoot = new ArrayList<Coordinates>();
	}

	//methods
	private int randomInt() {
		return (int)( (Math.random()*10) );
	}
	private Coordinates randomCoordinates() {
		Coordinates coordinates = new Coordinates();
		coordinates.giveValues(randomInt(),randomInt());
		return coordinates;
	}
	private Coordinates feasibleCoordinates(Coordinates coordinates, int len) {
		Coordinates returnCoordinates = new Coordinates();
		int direction = randomInt();
		if(direction >5) {//horizontal
			returnCoordinates.giveValues(coordinates.getRow(),coordinates.getCol()+(len-1));
			return returnCoordinates;
		}
		else {//vertical
			returnCoordinates.giveValues(coordinates.getRow()+(len-1),coordinates.getCol());
			return returnCoordinates;
		}

	}
	@Override
	public void placeFleet() {
		Coordinates startPosition=new Coordinates();
		Coordinates endPosition=new Coordinates();
		for(int i=0;i<NB_SHIPS;i++) {
			boolean correctPosition=false;

			getMyFleet()[i] = new Ship(SHIP_LENGTHS[i]);

			while(!correctPosition) {
				startPosition = randomCoordinates();
				endPosition = feasibleCoordinates(startPosition,SHIP_LENGTHS[i]);
				correctPosition = this.checkPlacing(getMyFleet()[i],startPosition, endPosition);
			}
			placeShip(getMyFleet()[i],startPosition, endPosition);
		}

	}

	public Coordinates makeGuess(Player enemy) {
		switch(this.difficulty) {
		case 1 : return makeGuessEasy();
		case 2 : return makeGuessMedium();
		case 3 : return makeGuessHard();
		default : return null ;

		}
	}

	private Coordinates makeGuessEasy() {
		return randomCoordinates();
	}

	private Coordinates makeGuessMedium() {
		Coordinates shootPosition=new Coordinates();
		shootPosition = randomCoordinates();
		while(this.getAlreadyShooted().contains(shootPosition)) {
			shootPosition = randomCoordinates();
		}
		getAlreadyShooted().add(shootPosition);
		return shootPosition;
	}

	private Coordinates makeGuessHard() {
		Coordinates shootPosition=new Coordinates();
		if(!this.getPriorityShoot().isEmpty()) {
			shootPosition = this.getPriorityShoot().get(0);
			this.getPriorityShoot().remove(0);
		}
		else {
			return makeGuessMedium();
		}
		return shootPosition;
	}

	public void updatePlayerActive(Coordinates guess, boolean isHit, int sunk, Ship shipHit) {//player send the shoot
		this.getAlreadyShooted().add(guess);
		this.getPriorityShoot().removeAll(this.getAlreadyShooted());
		if(isHit) {//if get, need to get correct values , remove non valid ones
			guess.setHit(true);
			this.getPriorityShoot().addAll(guess.getAllNeighbours());

		}
	}

	public void updatePlayerPassive(Coordinates guess, boolean isHit) {//player receive
		if(isHit) {
			this.getShip(guess).setHit(guess);
			this.setNumberOfHitsTaken();
		}

	}
}