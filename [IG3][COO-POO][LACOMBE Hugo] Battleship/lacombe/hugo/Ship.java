package lacombe.hugo;
import java.util.ArrayList;
public class Ship {

	public static final String[] SHIPS_NAMES = {"Destroyer","Submarine","Cruiser","Battleship","Carrier"};

	public int len;
	public int hitReceived ;
	public ArrayList<Coordinates> position;

	//getters, setters

	public int getLen() {
		return len;
	}

	public int getHitReceived() {
		return hitReceived;
	}

	public ArrayList<Coordinates> getPosition() {
		return position;
	}

	public void setPosition(ArrayList<Coordinates> position) {
		this.position = position;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public void upHitReceived() {
		hitReceived++;;
	}

	public boolean isHit(Coordinates c){
		for(Coordinates i : position) { 
			if(i.equals(c)) {
				return true ; };
		}
		return false;
	}

	public boolean isSunk(){
		for(Coordinates c : position){
			if(!c.isHit()){
				return false;
			}
		}
		return true;
	}
	//
	public Ship(int len) {
		this.len = len;
		position = new ArrayList<Coordinates>();
		hitReceived = 0 ;
	}
	//		

	public int isSunkThisTurn() {
		//function is true if the ships has just been sunk due to the previous hit
		if(this.getHitReceived() == this.getLen()) {
			return 0 ; }//already down
		else{ 
			this.upHitReceived();
			if(this.getHitReceived() == this.getLen()) {
				return 1 ;//down
			}
		}	
		return -1;//not down
	}

	public void updatePosition(Coordinates start, Coordinates end) {
		this.setPosition(this.calculateAllShipPosition(start, end));
	}

	public String name() {
		return SHIPS_NAMES[this.getLen()-1];
	}

	public void setHit(Coordinates c) {
		for(Coordinates coord : position){
			if(coord.equals(c)){
				coord.setHit(true);
			}
		}
	}
	
		public ArrayList<Coordinates> calculateAllShipPosition(Coordinates start, Coordinates end){
			ArrayList<Coordinates> allShipPositions = new ArrayList<Coordinates>();
			if(start.getRow() == end.getRow()){//ship placed horizontally
				for(int i=Math.min(start.getCol(),end.getCol());i <= Math.min(start.getCol(),end.getCol()) + len -1; i++){
					Coordinates newCoord = new Coordinates();
					newCoord.giveValues(start.getRow(), i) ;
					allShipPositions.add(newCoord);
				}
			}
			else{//ship placed vertically
				for(int i=Math.min(start.getRow(),end.getRow());i <= Math.min(start.getRow(),end.getRow()) + len -1; i++){
					Coordinates newCoord = new Coordinates();
					newCoord.giveValues(i, start.getCol()) ;
					allShipPositions.add(newCoord);
				}
			}
			return allShipPositions;
		}
}
