package world;
import java.util.Random;

import ast.ProgramImpl;

public class Critter extends Hex {
	
	public ProgramImpl genes;
	public int direction;
	public int [] mem = new int [w.MEM_SIZE];
	String name;
	public World w;
	public int row;
	public int column;
	public boolean matingdance;
	public Random r = new Random(); //this is useful to have for the random sense and I didn't want to keep generating random objects.
		
	/**Sets up the instance variables for a critter. Data is given in the order
	 * specified in 4.1 of the a5 spec.
	 * @param species
	 * @param data
	 */
	
	public Critter(String species, int [] data) { 
	}
	
	public Critter(int [] mem, Random r, ProgramImpl genetics, World wrld) {
		genes = genetics;
		direction = 0;
		w = wrld;
		this.mem = mem;
		matingdance = false;
		this.r = r;
	}
	
	/**Effect: updates the critter's instance variables according to its rules. 
	 * When the critter moves, you might have to create a new hex on the place where it was.
	 */
	public void timestep(){
		matingdance = false;
		Rulehandler.altercritter(this);
	}
	@Override
	public int getNumRep() {
		// TODO Auto-generated method stub
		return 0;
	}
}

