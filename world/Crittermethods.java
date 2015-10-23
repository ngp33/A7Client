package world;

import ast.Actionpacked;

public class Crittermethods {
	
	/** should handle both cases of eating
	 * 	1. When there is more food than the critter can consume
	 * 	2. When the critter can consume all the food on the hex.
	 * After the critter has eaten, it loses the energy for the eating action.
	 * @param c
	 * TODO this makes it so a critter cant really ever be full. Is that alright?
	 */
	public static void consume(Critter c) {
		c.mem[4] -= c.mem[3];
		if (c.mem[4] <= 0) {
			c.dies();
		}
		int n = c.w.getNumRep(dircoords(c,true));
		if (n < c.w.ROCK_VALUE) {
			if (c.w.ENERGY_PER_SIZE * c.mem[3] < c.mem [4] + (c.w.ROCK_VALUE-n)) { //too much food on the hex to be fully consumed
				c.w.putFood((c.w.ROCK_VALUE-n) - (c.w.ENERGY_PER_SIZE * c.mem[3] - c.mem[4]), dircoords(c,true));
				c.mem[4] = c.w.ENERGY_PER_SIZE * c.mem[3];
			}
			else{
				c.mem[4] += (c.w.ROCK_VALUE - n);
				c.w.putEmpty(dircoords(c,true));
			}
		}
	}

	/**Updates the energy of the attacked critter.
	 * Invariant: there actually is a critter that is being attacked...
	 * @param attacker
	 * @param victim
	 */
	 //TODO make it truncate, not round
	public static void attack(Critter attacker){
		int [] c = dircoords(attacker, true);
		Hex victim = attacker.w.getHex(c[0], c[1]);
		if (victim instanceof Critter) {
			Critter v = (Critter) victim;
			double inside = attacker.w.DAMAGE_INC * (attacker.mem[3] * attacker.mem[2] - v.mem[3] * v.mem[1]);
			int harm = Math.round((float) (attacker.w.BASE_DAMAGE * attacker.mem[3] * pfunct(inside)));
			v.mem[4] -= harm;
			if (v.mem[4] <= 0) {
				dies(v);
			}
		}
		attacker.mem[4] -= attacker.mem[3] * attacker.w.ATTACK_COST;
	}
	
	/** used in calculating the attack damage*/
	private static double pfunct(double val){
		return 1/(1 + Math.exp(val));
	}
	
	
	/** moves the critter TODO check that the move is valid and update world hex grid.
	 * Invariant: direction is between 0 and 5 inclusive*/
	public static void movement(Critter c, boolean forward) {
		int [] newplace = dircoords(c,forward);
		if (checkempty(c, forward)) {
			c.w.swap(c, c.w.getHex(newplace[0], newplace[1]));
		}
		c.mem[4] -= c.mem[3] * 3;
	}
	
	
	/**
	 * 
	 * @param c
	 * @param ahead -- whether we are looking ahead or behind the critter (needed for movement)
	 * @return the coordinates of the space ahead of the critter (based on its
	 * orientation.
	 */
	public static int [] dircoords(Critter c, boolean ahead){
		int dir = ahead ? 1 : -1;
		dir = c.direction >= 3 ? -dir : dir;
		int tempdir = c.direction % 3;
		int row = c.row;
		int col = c.col;
		if (tempdir < 2){
			row += dir;
		}
		if (tempdir == 1 || tempdir == 2){
			col += dir;
		}
		return new int [] {row, col};
		
	}
	
	
	/**Turns the critter left or right.
	 * I don't think any additional check is required.*/
	public static void turn(Critter c, boolean left) {
		c.direction += 6;
		c.direction = left ? c.direction - 1 : c.direction + 1;
		c.direction = c.direction % 6;
		c.mem[4] -= c.mem[3];
	}
	
	/**returns the integer result of the complexity formula
	 * 
	 * @param c
	 * @return
	 */
	public static int complexitycalc(Critter c){
		return (c.genes.children.length) * c.w.RULE_COST + (c.mem[1] + c.mem[2]) * c.w.ABILITY_COST;
	}
	
	/**Effect: performs the wait action on c. Does nothing if the
	 * critter is at maximum energy level.
	 * @param c
	 */
	public static void wait(Critter c){
		c.mem[4] += c.w.SOLAR_FLUX * c.mem[3];
		c.mem[4] = c.mem[4] > c.mem[3] * c.w.ENERGY_PER_SIZE ? c.mem[3] * c.w.ENERGY_PER_SIZE : c.mem[4];
	}
	
	
	/** Checks to see if the space one ahead if {@code ahead = true} in the critter's direction
	 * 	or one behind if {@code ahead = false} is empty.
	 * 
	 * @param c
	 * @param ahead
	 */
	public static boolean checkempty(Critter c, boolean ahead){
		return c.w.getNumRep(dircoords(c,ahead)) == 0 ? true : false;
	}
	
	
	
	
	/**Generates a new food hex with the proper amount of food in the place where the 
	 * critter was when it died. //TODO implement
	 * @param c
	 */
	public static void dies(Critter c){
		c.w.replace(new Food(c.w.FOOD_PER_SIZE * c.mem[3]), c);
	}
	
	/** Critter grows one unit bigger.*/
	public static void grow(Critter c) {
		c.mem[4] -= c.mem[3] * complexitycalc(c) * c.w.GROW_COST;
		if (c.mem[3] < 99) {
			c.mem[3] ++;
		}
	}
	
	
	/** A critters attempt to mate TODO make sure this handles energy consumption for
	 * unsuccessful mating*/
	public static void mate(Critter c) {
		c.mem[4] -= c.mem[3];
		ActionMate.matewith(c);
	}
	
	public static void asexual(Critter c) {
		ActionMate.alone(c);
	}

	/**handles serve. Note, Critter energy may go below 0 in this action */
	public static void serve(Critter c, int quantity) {
		if (checkempty(c, true)) {
			int amount = c.mem[4] >= quantity ? quantity : c.mem[4];
			c.w.putFood(amount, dircoords(c,true));
		}
		c.mem[4] -= quantity;
	}

	public static void tag(Critter c, int num) {
		int [] place = dircoords(c, true);
		if (c.w.getNumRep(place) > 0) {
			Critter other = (Critter) c.w.getHex(place[0], place[1]);
			other.mem[6] = num;
		}
		c.mem[4] -= c.mem[3];
		
	}
	
	
}