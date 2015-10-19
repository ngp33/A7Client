package world;

public class Rulehandler {
	
	/**Effect: alters the critters instance variables according to its rules
	 * Invariant: c.mem[5] = 1*/
	public static void altercritter(Critter c){
		while (c.mem[5] < 999 && !evaluateprogram(c)){
			c.mem[5] ++;
		}
		c.mem[5] = 1;
	}
	
	
	/**Effect: Evaluates the program of a critter.
	 * Returns true if the rule committed terminates the rule evaluation phase (ie. it commits an action).
	 * @param c
	 * @return
	 */
	private static boolean evaluateprogram(Critter c){
		return c.genes.eval(c);
	}


}
