package critter;

import ast.ProgramImpl;

public class Critter { //TODO make it extend hex
	public ProgramImpl genes;
	public int direction;
	public int [] mem = new int [8];
	String name;
	public World w; //TODO make a world class.
	public int row;
	public int column;
	
	/**Sets up the instance variables for a critter. Data is given in the order
	 * specified in 4.1 of the a5 spec.
	 * @param species
	 * @param data
	 */
	public Critter(String species, int [] data) { 
	}

	/**Effect: updates the critter's instance variables according to its rules. 
	 * When the critter moves, you might have to create a new hex on the place where it was.
	 */
	public void timestep(){
		Rulehandler.altercritter(this);
	}
}
