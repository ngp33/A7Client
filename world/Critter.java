package world;
import java.util.Random;
import ast.ProgramImpl;

public class Critter extends Hex {
	
	public ProgramImpl genes;
	public int direction;
	public World w;
	public int [] mem;
	String name;
	public boolean matingdance;
	public Random r = new Random(); //this is useful to have for the random sense and I didn't want to keep generating random objects.
		
	/**Sets up the instance variables for a critter. Data is given in the order
	 * specified in 4.1 of the a5 spec.
	 * @param species
	 * @param data
	 */
	//TODO make this
	
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
	
	public void consume(){
		Crittermethods.consume(this);
	}
	
	public void attack(){
		Crittermethods.attack(this);
	}
	
	public void movement(boolean forward){
		Crittermethods.movement(this,forward);
	}
	
	public void turn(boolean left) {
		Crittermethods.turn(this, left);
	}
	
	public void waiting(){
		Crittermethods.wait(this);
	}
	
	public void dies() {
		Crittermethods.dies(this);
	}
	
	public void grow() {
		Crittermethods.grow(this);
	}
	
	public void mate() {
		Crittermethods.mate(this);
	}
	
	public void bud() {
		Crittermethods.asexual(this);
	}
	
	public void serve(int amount) {
		Crittermethods.serve(this, amount);
	}
	
	public void youreit(int num) {
		Crittermethods.tag(this, num);
	}
	
	public int nearby(int num) {
		return Crittersenses.nearby(this, num);
	}
	
	public int ahead(int num) {
		return Crittersenses.spacesahead(this, num);
	}
	
	public int smell() {
		return 0;
	}
	
	public int random(int num) {
		return Crittersenses.rando(this, num);
	}
	
}

