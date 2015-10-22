package world;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import parse.Parser;
import parse.ParserImpl;

public class World {
	
	Hex[][] grid;
	int time;
	
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
	
	
	public World(int numRows, int numCols) {
		if (numRows < numCols/2 + 1) {
			//TODO What to do?
		}
		rows = numRows;
		columns = numCols;
		
		//grid = new Hex[numCols][numRows - (numCols + 1) / 2];
		grid = new Hex[numCols][numRows - numCols/2];
		
		time = 0;
		
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
			e.printStackTrace();
			System.exit(0);
		}
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
	
	public void advanceTime(int amount) {
		time += amount;
	}

}
