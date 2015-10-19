package ast;

import ast.Action.Hamlet;
import world.Critter;
import java.math.*;

public class Actionpacked {
	
	/** Effect: commits the action and updates fields (mostly energy, for the critter which
	 * commits the action
	 * @param c
	 * @param type
	 */
	public static void themove(Critter c, Hamlet type){
		switch(type){
		
		case backward:
			movement(c,false);
			break;
		case bud: //TODO overload critter constructor to allow for bud
			break;
		case eat:
			consume(c);
			break;
		case forward:
			movement(c,true);
			break;
		case grow:
			grow(c);
			break;
		case left:
			turn(c,true);
			break;
		case mate:
			break;
		case right:
			turn(c,false);
			break;
		case serve:
			break;
		case tag:
			break;
		case wait:
			wait(c);
			break;
		default:
			break;
		
		}
	}
	
	private static void consume(Critter c) {
		int n = c.w.getNumRep(dircoords(c,true));
		if (n < 0) {
			if (c.w.ENERGY_PER_SIZE * c.mem[3] < c.mem [4] + (-1-n)) { //too much food on the hex to be fully consumed
				c.w.putFood()
			}
		}
	}

	/**Updates the energy of the attacked critter. Does not handle the situation where
	 * the critter dies. Maybe that's something TODO
	 * Invariant: there actually is a critter that is being attacked...
	 * @param attacker
	 * @param victim
	 */
	public static void attack(Critter attacker, Critter victim){
		double inside = attacker.w.DAMAGE_INC * (attacker.mem[3] * attacker.mem[2] - victim.mem[3] * victim.mem[1]);
		int harm = (int) (attacker.w.BASE_DAMAGE * attacker.mem[3] * pfunct(inside));
		victim.mem[4] -= harm;
		attacker.mem[4] -= attacker.mem[3] * attacker.w.ATTACK_COST;
	}
	
	/** used in calculating the attack damage*/
	private static double pfunct(double val){
		return 1/(1 + Math.exp(val));
	}
	
	
	/** moves the critter TODO check that the move is valid.
	 * Invariant: direction is between 0 and 5 inclusive*/
	private static void movement(Critter c, boolean forward){
		int [] newplace = dircoords(c,forward);
		c.row = newplace[0];
		c.column = newplace[1];
		//Make sure this only updates if the move is successful
		c.mem[4] -= c.mem[3] * 3;
	}
	
	
	/**
	 * 
	 * @param c
	 * @param ahead -- whether we are looking ahead or behind the critter (needed for movement)
	 * @return the coordinates of the space ahead of the critter (based on its
	 * orientation.
	 */
	private static int [] dircoords(Critter c, boolean ahead){
		int dir = ahead ? 1 : -1;
		dir = c.direction >= 3 ? -dir : dir;
		int tempdir = c.direction % 3;
		int row = c.row;
		int col = c.column;
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
	private static void turn(Critter c, boolean left) {
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
	private static int complexitycalc(Critter c){
		return (c.genes.children.length) * c.w.RULE_COST + (c.mem[1] + c.mem[2]) * c.w.ABILITY_COST;
	}
	
	private static void wait(Critter c){
		c.mem[4] += c.w.SOLAR_FLUX * c.mem[3];
	}
	
	
	/** Checks to see if the space one ahead if {@code ahead = true} in the critter's direction
	 * 	or one behind if {@code ahead = false} is empty.
	 * 
	 * @param c
	 * @param ahead
	 */
	private static boolean checkempty(Critter c, boolean ahead){
		return c.w.getNumRep(dircoords(c,ahead)) == 0 ? true : false;
	}
	
	
	
	
	/**Generates a new food hex with the proper amount of food in the place where the 
	 * critter was when it died. //TODO implement
	 * @param c
	 */
	public static void dies(Critter c){
		
	}
	
	/** Critter grows one unit bigger.*/
	private static void grow(Critter c) {
		c.mem[4] -= c.mem[3] * complexitycalc(c) * c.w.GROW_COST;
		c.mem[3] ++;
	}

	
}
