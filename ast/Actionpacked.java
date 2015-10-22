package ast;

import ast.Action.Hamlet;
import world.Critter;
import world.Hex;

import java.math.*;

public class Actionpacked {
	
	/** Effect: commits the action and updates fields (mostly energy, for the critter which
	 * commits the action
	 * @param c
	 * @param type
	 */
	public static void themove(Critter c, Hamlet type){
		switch(type){
		case attack:
			attack(c);
		case backward:
			movement(c,false);
			break;
		case bud:
			asexual(c);
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
			mate(c);
			break;
		case right:
			turn(c,false);
			break;
		/*case serve:
			break;
		case tag:
			break;*/ //Should be handled in SpecAction
		case wait:
			wait(c);
			break;
		default:
			break;
		
		}
	}
	
	/** should handle both cases of eating
	 * 	1. When there is more food than the critter can consume
	 * 	2. When the critter can consume all the food on the hex.
	 * After the critter has eaten, it loses the energy for the eating action.
	 * @param c
	 * TODO this makes it so a critter cant really ever be full. Is that alright?
	 */
	private static void consume(Critter c) {
		int n = c.w.getNumRep(dircoords(c,true));
		if (n < 0) {
			if (c.w.ENERGY_PER_SIZE * c.mem[3] < c.mem [4] + (-1-n)) { //too much food on the hex to be fully consumed
				c.w.putFood((-1-n) - (c.w.ENERGY_PER_SIZE * c.mem[3] - c.mem[4]), dircoords(c,true));
				c.mem[4] = c.w.ENERGY_PER_SIZE * c.mem[3];
			}
			else{
				c.mem[4] += (-1-n);
				c.w.putFood(0,dircoords(c,true));
			}
		}
		c.mem[4] -= c.mem[3];
	}

	/**Updates the energy of the attacked critter.
	 * Invariant: there actually is a critter that is being attacked...
	 * @param attacker
	 * @param victim
	 */
<<<<<<< HEAD
	public static void attack(Critter attacker, Critter victim){
		double inside = attacker.w.DAMAGE_INC * (attacker.mem[3] * attacker.mem[2] - victim.mem[3] * victim.mem[1]);
		int harm = Math.round((attacker.w.BASE_DAMAGE * attacker.mem[3] * pfunct(inside)));
		victim.mem[4] -= harm;
=======
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
>>>>>>> Evaluator-in-progress
		attacker.mem[4] -= attacker.mem[3] * attacker.w.ATTACK_COST;
		if (victim.mem[4] < 0) {
			dies(victim);
		}
	}
	
	/** used in calculating the attack damage*/
	private static double pfunct(double val){
		return 1/(1 + Math.exp(val));
	}
	
	
	/** moves the critter TODO check that the move is valid.
	 * Invariant: direction is between 0 and 5 inclusive*/
	private static void movement(Critter c, boolean forward) {
		int [] newplace = dircoords(c,forward);
		c.row = newplace[0];
		c.column = newplace[1];
<<<<<<< HEAD
		//TODO Make sure this only updates if the move is successful... actually probably not
=======
>>>>>>> Evaluator-in-progress
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
	
	private static void wait(Critter c){
		c.mem[4] += c.w.SOLAR_FLUX * c.mem[3];
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
		c.w.putFood(c.w.FOOD_PER_SIZE * c.mem[3], new int [] {c.row, c.column});
	}
	
	/** Critter grows one unit bigger.*/
	private static void grow(Critter c) {
		c.mem[4] -= c.mem[3] * complexitycalc(c) * c.w.GROW_COST;
		c.mem[3] ++;
	}
	
	
	/** A critters attempt to mate TODO make sure this handles energy consumption for
	 * unsuccessful mating*/
	private static void mate(Critter c) {
		c.mem[4] -= c.mem[3];
		ActionMate.matewith(c);
	}
	
	private static void asexual(Critter c) {
		ActionMate.alone(c);
	}
	
	
}
