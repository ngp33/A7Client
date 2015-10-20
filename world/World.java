package world;

public class World {
	
	Hex[][] grid;
	int time;
	
	public Hex getHex(int row, int col) {
		row -= row/2;
		
		return grid[col][row];
	}
	
	public void setHex(int row, int col, Hex h) {
		row -= row/2;
		
		grid[col][row] = h;
	}
	
	
	public int getNumRep(int [] rowcommacol) {
		return getHex(rowcommacol[0], rowcommacol[1]).getNumRep();
		
	}
	
	public void putFood(int amount, int [] rowcommacol) {
		//TODO
	}

}
