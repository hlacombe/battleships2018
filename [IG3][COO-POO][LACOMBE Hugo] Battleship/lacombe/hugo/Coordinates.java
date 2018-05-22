package lacombe.hugo;

import java.util.ArrayList;

public class Coordinates {
	
	public static int maxRow = 10;
	public static int maxCol = 10;
	
	private int row;
	private int col;
	private boolean isHit;
	private int proba;
	
	public Coordinates() {
		this.isHit = false;
		
	}
	public Coordinates(int r, int c) {
		this.row = r;
		this.col=c;
		this.isHit = false;
		proba=0;
	}
	public void giveValues(int r, int c) {
		this.setRow(r);
		this.setCol(c);
	}
	
	public boolean correctCoordinates() {
		return this.getRow()<maxRow && this.getCol()<maxCol && this.getRow()>=0 && this.getCol()>=0;
	}
	public boolean isHit() {
		return isHit;
	}

	public void setHit(boolean b) {
		this.isHit = b;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
	public int getProba() {
		return proba;
	}
	public void setProba() {
		this.proba++;
	}
	public void setProba(int i) {
		this.proba = i;
	}
	@Override
	public boolean equals(Object c) {
		if(c == null) { return false; }
		if(c instanceof Coordinates) { return this.row == ((Coordinates) c).getRow() && this.col==((Coordinates) c).getCol(); }
		return false;
	}
	@Override
	public String toString() {
		return "[" + row + " " + col+ "]";
	}
	
	public ArrayList<Coordinates> getAllNeighbours(){
		ArrayList<Coordinates> neighbours = new ArrayList<Coordinates>();
		neighbours.addAll(getColNeighbours());
		neighbours.addAll(getRowNeighbours());
		return neighbours;
	}
	public ArrayList<Coordinates> getRowNeighbours(){
		Coordinates v1 = new Coordinates(this.row,this.col+1);
		Coordinates v2 = new Coordinates(this.row,this.col-1);
		ArrayList<Coordinates> neighbours = new ArrayList<Coordinates>();
		if(v1.correctCoordinates()) {neighbours.add(v1);};
		if(v2.correctCoordinates()) {neighbours.add(v2);};
		return neighbours;

	}
	public ArrayList<Coordinates> getColNeighbours(){
		Coordinates v1 = new Coordinates(this.row+1,this.col);
		Coordinates v2 = new Coordinates(this.row-1,this.col);
		ArrayList<Coordinates> neighbours = new ArrayList<Coordinates>();
		if(v1.correctCoordinates()) {neighbours.add(v1);};
		if(v2.correctCoordinates()) {neighbours.add(v2);};
		return neighbours;
	}
}
