package lacombe.hugo;
import java.util.ArrayList;

public class Player{

	protected static final int NB_SHIPS = 5;
	protected static final int[] SHIP_LENGTHS = {2, 3, 3, 4, 5};
	protected static final String[] SHIPS_NAMES = {"Destroyer","Submarine","Cruiser","Battleship","Carrier"};
	protected static final String[] LINE_NAME = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

	//public static final int NB_SHIPS = 1;
	//public static final int[] SHIP_LENGTHS = {2}; // sum = 17


	private Ship[] myFleet;
	private int numberOfHitsTaken;
	private ArrayList<Coordinates> alreadyShooted ;

	public Player() {
		myFleet = new Ship[NB_SHIPS];
		alreadyShooted = new ArrayList<Coordinates>();
		numberOfHitsTaken = 0;
	}

	public void placeFleet() {
		Coordinates startPosition=new Coordinates();
		Coordinates endPosition=new Coordinates();
		for(int i=0;i<NB_SHIPS;i++) {
			boolean correctPosition=false;

			myFleet[i] = new Ship(SHIP_LENGTHS[i]);

			while(!correctPosition) {
				System.out.println("Place your " +SHIPS_NAMES[i] +" , length "+SHIP_LENGTHS[i] );
				System.out.println("First Coordinates");
				startPosition = ToolBox.askPosition();
				System.out.println("Second Coordinates");
				endPosition = ToolBox.askPosition();

				correctPosition = this.checkPlacing(myFleet[i],startPosition, endPosition);
				if(!correctPosition) {
					System.out.println("The position isn't correct!");
				}
			}
			System.out.println(SHIPS_NAMES[i]+" correctly placed!");
			placeShip(myFleet[i],startPosition, endPosition);
		}

	}

	public boolean checkPlacing(Ship s, Coordinates start, Coordinates end) {
		if(!start.correctCoordinates() || !end.correctCoordinates()) {
			return false;
		}
		if(!((start.getCol()==end.getCol())) && !((start.getRow()==end.getRow())) ) return false;
		if(start.getCol()==end.getCol() ) {
			if(Math.max(start.getRow(),end.getRow()) == (Math.min(start.getRow(),end.getRow()) + (s.getLen()-1))) {
				return false;
			}
		}
		if(start.getRow()==end.getRow()){
			if(!((Math.max(start.getCol(),end.getCol()))==(Math.min(start.getCol(),end.getCol()) + (s.getLen()-1)))) {
				return false;
			}
		}
		ArrayList<Coordinates> allShipPositions = s.calculateAllShipPosition(start, end);
		for(Ship x : this.getMyFleet()){
			if(x !=null) {
				for(Coordinates c : x.getPosition()) {
					for(Coordinates test : allShipPositions)
						if(c.equals(test) || !test.correctCoordinates()) {
							return false;
						}	
				}
			}
		}
		return true;
	}

	public void placeShip(Ship ship, Coordinates start, Coordinates end) {
		ship.updatePosition(start,end);
	}

	public Coordinates makeGuess(Player player) {
		Coordinates shootPosition=new Coordinates();
		shootPosition = ToolBox.askPosition();
		return shootPosition;
	}

	public boolean checkGuess(Coordinates guess) {
		for(Ship s : this.getMyFleet()){
			if(s.isHit(guess)){
				return true;
			}
		}
		return false ;
	}

	public void updatePlayerActive(Coordinates guess, boolean isHit, int Sunk, Ship shipHit) {//player send the shoot
		this.getAlreadyShooted().add(guess);

	}

	public void updatePlayerPassive(Coordinates guess, boolean isHit) {//player receive
		if(isHit) {
			this.getShip(guess).setHit(guess);
			this.setNumberOfHitsTaken();
		}

	}

	public Ship getShip(Coordinates guess) {
		//return the ship concerned by the coordinates given, null if no ships are corresponding
		for(Ship s : this.getMyFleet()) {
			for(Coordinates c : s.getPosition()) {
				if(c.equals(guess)) {
					return s;
				}
			}
		}
		return null;
	}

	public boolean hasLost() {
		for(Ship s : this.getMyFleet()){
			if(s.getLen() != s.getHitReceived()){
				return false;
			}
		}
		return true;
	}
	//getters and setters
	public Ship[] getMyFleet() {
		return myFleet;
	}

	public void setMyFleet(Ship[] myFleet) {
		this.myFleet = myFleet;
	}

	public int getNumberOfHitsTaken() {
		return numberOfHitsTaken;
	}

	public void setNumberOfHitsTaken() {
		numberOfHitsTaken++;
	}

	public ArrayList<Coordinates> getAlreadyShooted() {
		return alreadyShooted;
	}

	public void setAlreadyShooted(ArrayList<Coordinates> alreadyShooted) {
		this.alreadyShooted = alreadyShooted;
	}

	public void printMyGrid(Player p) {
		ArrayList<Coordinates> enemyList = p.getAlreadyShooted();
		System.out.print("My Grid\n");
		System.out.print(" 12345678910\n");
		for(int i=0;i<10;i++) {
			System.out.print(LINE_NAME[i]);
			for(int j=0;j<10;j++) {
				Coordinates tmp = new Coordinates(i,j);
				if(getShip(tmp)!=null && enemyList.contains(tmp)) {
					System.out.print("x");//ship hit by the enemy
				}
				if(getShip(tmp)!=null && !enemyList.contains(tmp)) {
					System.out.print("o");//ship safe
				}
				if(enemyList.contains(tmp) && getShip(tmp)==null) {
					System.out.print("m");//enemy miss
				}
				if(!enemyList.contains(tmp) && getShip(tmp)==null) {
					System.out.print("~");//nothing
				}
			}
			System.out.println("\n");
		}
	}

	public void printEnnemyGrid(Player p) {
		System.out.print("Ennemy Grid\n");
		System.out.print(" 12345678910\n");
		ArrayList<Coordinates> enemyShipCoordinates = new ArrayList<Coordinates>();
		for(Ship s : p.getMyFleet()) {
			for(Coordinates c :s.getPosition()) {
				enemyShipCoordinates.add(c);
			}
		}
		for(int i=0;i<10;i++) {
			System.out.print(LINE_NAME[i]);
			for(int j=0;j<10;j++) {
				Coordinates tmp = new Coordinates(i,j);
				if(this.getAlreadyShooted().contains(tmp) && enemyShipCoordinates.contains(tmp)) {
					System.out.print("x");//confirmed ship hit
				}
				if(this.getAlreadyShooted().contains(tmp) && !enemyShipCoordinates.contains(tmp)) {
					System.out.print("m");//confirmed miss
				}
				if(!this.getAlreadyShooted().contains(tmp)) {
					System.out.print("?");//ship hit by the enemy
				}
			}
			System.out.println("\n");
		}
	}


	public void print(Player opponent) {
		printMyGrid(opponent);
		printEnnemyGrid(opponent);
	}
}
