package world;

public class World {
	
	Hex[][] grid;
	int time;
	
	int rows;
	int columns;
	
	public final int BASE_DAMAGE = 100;
	public final float DAMAGE_INC = 0.2f;
	public final int ENERGY_PER_SIZE = 500;
	public final int FOOD_PER_SIZE = 200;
	public final int MAX_SMELL_DISTANCE = 10;
	public final int ROCK_VALUE = -1;
	public final int COLUMNS = 50;
	public final int ROWS = 68;
	public final int MAX_RULES_PER_TURN = 999;
	public final int SOLAR_FLUX = 1;
	public final int MOVE_COST = 3;
	public final int ATTACK_COST = 5;
	public final int GROW_COST = 1;
	public final int BUD_COST = 9;
	public final int MATE_COST = 5;
	public final int RULE_COST = 2;
	public final int ABILITY_COST = 25;
	public final int INITIAL_ENERGY = 250;
	public final int MIN_MEMORY = 8;
	
	
	public World(int numRows, int numCols) {
		if (numRows < numCols/2 + 1) {
			//TODO What to do?
		}
		rows = numRows;
		columns = numCols;
		
		//grid = new Hex[numCols][numRows - (numCols + 1) / 2];
		grid = new Hex[numCols][numRows - numCols/2];
		
		time = 0;
	}
	
	public Hex getHex(int row, int col) {
		if (isInGrid(row, col)) {
			row -= row/2;
			
			return grid[col][row];
		} else {
			return new Rock();
		}
	}
	
	public void setHex(int row, int col, Hex h) {
		row -= row/2;
		
		grid[col][row] = h;
	}
	
	public boolean isInGrid(int row, int col) {
		int i = 2*row - col;
		return (i >= 0 && i < 2*rows - columns);
	}
	
	public int getNumRep(int [] rowcommacol) {
		return getHex(rowcommacol[0], rowcommacol[1]).getNumRep();
	}
	
	public void putFood(int amount, int [] rowcommacol) {
		//TODO
	}

}
