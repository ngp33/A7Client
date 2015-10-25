package world;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import parse.Parser;
import parse.ParserImpl;

public class World {
	
	Hex[][] grid;
	ArrayList<Critter> critters;
	
	String name; // Not sure what this is for yet but it's included in the world file.
	int time = 0;
	int rows;
	int columns;
	
	public final int BASE_DAMAGE;
	public final float DAMAGE_INC;
	public final int ENERGY_PER_SIZE;
	public final int FOOD_PER_SIZE;
	public final int MAX_SMELL_DISTANCE;
	public final int ROCK_VALUE;
	public final int COLUMNS;
	public final int ROWS;
	public final int MAX_RULES_PER_TURN;
	public final int SOLAR_FLUX;
	public final int MOVE_COST;
	public final int ATTACK_COST;
	public final int GROW_COST;
	public final int BUD_COST;
	public final int MATE_COST;
	public final int RULE_COST;
	public final int ABILITY_COST;
	public final int INITIAL_ENERGY;
	public final int MIN_MEMORY;
	
	
	public World() {
		name = "Untitled";
		critters = new ArrayList<Critter>();
		
		// This would look cleaner in its own method, but I need to do it inside the constructor.
		try {
			FileReader f = new FileReader("constants.txt");
			
			BASE_DAMAGE = getNumberFromLine(f);
			DAMAGE_INC = getNumberFromLine(f);
			ENERGY_PER_SIZE = getNumberFromLine(f);
			FOOD_PER_SIZE = getNumberFromLine(f);
			MAX_SMELL_DISTANCE = getNumberFromLine(f);
			ROCK_VALUE = getNumberFromLine(f);
			COLUMNS = getNumberFromLine(f);
			ROWS = getNumberFromLine(f);
			MAX_RULES_PER_TURN = getNumberFromLine(f);
			SOLAR_FLUX = getNumberFromLine(f);
			MOVE_COST = getNumberFromLine(f);
			ATTACK_COST = getNumberFromLine(f);
			GROW_COST = getNumberFromLine(f);
			BUD_COST = getNumberFromLine(f);
			MATE_COST = getNumberFromLine(f);
			RULE_COST = getNumberFromLine(f);
			ABILITY_COST = getNumberFromLine(f);
			INITIAL_ENERGY = getNumberFromLine(f);
			MIN_MEMORY = getNumberFromLine(f);
		} catch (IOException e) {
			System.out.println("constants.txt does not exist or is not correct.");
			throw new RuntimeException(); // Not sure what exception to throw when constants.txt is bad.
		}
		
		setupGrid(ROWS, COLUMNS);
	}
	
	public World(int numRows, int numCols, String n) {
		name = n;
		critters = new ArrayList<Critter>();
		
		try {
			FileReader f = new FileReader("constants.txt");
			
			BASE_DAMAGE = getNumberFromLine(f);
			DAMAGE_INC = getNumberFromLine(f);
			ENERGY_PER_SIZE = getNumberFromLine(f);
			FOOD_PER_SIZE = getNumberFromLine(f);
			MAX_SMELL_DISTANCE = getNumberFromLine(f);
			ROCK_VALUE = getNumberFromLine(f);
			COLUMNS = getNumberFromLine(f);
			ROWS = getNumberFromLine(f);
			MAX_RULES_PER_TURN = getNumberFromLine(f);
			SOLAR_FLUX = getNumberFromLine(f);
			MOVE_COST = getNumberFromLine(f);
			ATTACK_COST = getNumberFromLine(f);
			GROW_COST = getNumberFromLine(f);
			BUD_COST = getNumberFromLine(f);
			MATE_COST = getNumberFromLine(f);
			RULE_COST = getNumberFromLine(f);
			ABILITY_COST = getNumberFromLine(f);
			INITIAL_ENERGY = getNumberFromLine(f);
			MIN_MEMORY = getNumberFromLine(f);
		} catch (IOException e) {
			System.out.println("constants.txt does not exist or is not correct.");
			throw new RuntimeException(); // Not sure what exception to throw when constants.txt is bad.
		}
		
		if (numRows < numCols/2 + 1) {
			setupGrid(ROWS, COLUMNS);
		}
		setupGrid(numRows, numCols);
	}
	
	
	private void setupGrid(int numRows, int numCols) {
		rows = numRows;
		columns = numCols;
		
		//grid = new Hex[numCols][numRows - (numCols + 1) / 2];
		grid = new Hex[numCols][numRows - numCols/2];
		
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				grid[i][j] = new Food(0); // The hexes in the grid array that aren't part of the world will be
										  // made rocks by getHex.
			}
		}
	}
	
	public int getNumColumns() {
		return columns;
	}
	
	public int getNumRows() {
		return rows;
	}
	
	private int getNumberFromLine(FileReader f) throws IOException {
		String num = "";
		char c = 0;
		
		while (c != '\n') {
			c = (char) f.read();
			if (c > 47 && c < 58) {
				num = num + c;
			}
		}
		
		return Integer.parseInt(num);
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
		if (isInGrid(row, col)) {
			row -= row/2;
			
			grid[col][row] = h;
		}
	}
	
	public boolean isInGrid(int row, int col) {
		int i = 2*row - col;
		return (col >= 0 && col < columns && i >= 0 && i < 2*rows - columns);
	}
	
	public int getNumRep(int [] rowcommacol) {
		return getHex(rowcommacol[0], rowcommacol[1]).getNumRep();
	}
	
	/**Clears a hex of whatever was on it before and puts a certain amount of food on it
	 * Note, putfood -1, rowcommacol returns a
	 * 
	 * @param amount
	 * @param rowcommacol
	 */
	public void putFood(int amount, int [] rowcommacol) {
		replace(new Food(amount), getHex(rowcommacol[0], rowcommacol[1]));
	}
	
	/**Makes a new hex without any food on it */
	public void putEmpty(int [] rowcommacol) {
		replace(new Food(-1), getHex(rowcommacol[0], rowcommacol[1]));
	}
	
	/**Effect: Swaps the position of hexes one and two. It updates their
	 * internal row/col information as well as the world's 
	 * row/col information of them
	 * @param one
	 * @param two
	 */
	public void swap(Hex one, Hex two) {
		int temprow = one.row;
		int tempcol = one.col;
		one.row = two.row;
		one.col = two.col;
		two.row = temprow;
		two.col = tempcol;
		setHex(one.row, one.col, one);
		setHex(two.row, two.col, two);
	}
	
	/**Effect: It replaces the hex goner with one. Again, this updates,
	 * the hex position pointers within hex one and within the world. Hex one
	 * need not have an initialized row, col.
	 * @param one
	 * @param goner
	 */
	public void replace(Hex one, Hex goner) {
		one.row = goner.row;
		one.col = goner.col;
		setHex(one.row, one.col, one);
		// Has the hex already been checked to be of type Food?
		//Food f = (Food) getHex(rowcommacol[0], rowcommacol[1]);
		//f.addFood(amount);
	}
	
	public void addCritter(Critter c) {
		critters.add(c);
	}
	
	public void advance() {
		for (Critter c : critters) {
			c.timestep(); // Executes critter's program?
		}
		time++;
	}
	
	public void advanceTime(int amount) {
		for (int i = 0; i < amount; i++) {
			advance();
		}
	}
	
	public StringBuilder getInfo() {
		StringBuilder result = new StringBuilder();
		result.append("Time elapsed: " + time + "\n");
		result.append("Critters alive: " + critters.size() + "\n");
		appendASCIIMap(result);
		
		return result;
	}
	
	private void appendASCIIMap(StringBuilder sb) {
		boolean evenNumRows = grid.length % 2 == 0;
		boolean even = evenNumRows;
		int i = grid[0].length - 1;
		
		while (i >= 0) {
			if (even) {
				for (int j = 1; j < grid.length; j+=2) {
					sb.append("  ");
					sb.append(grid[j][i].getASCIIRep());
					sb.append(" ");
				}
				sb.append("\n");
			} else {
				sb.append(grid[0][i].getASCIIRep());
				for (int j = 2; j < grid.length; j+=2) {
					sb.append("   ");
					sb.append(grid[j][i].getASCIIRep());
				}
				sb.append("\n");
				i--;
			}
			even = !even;
		}
	}

}
