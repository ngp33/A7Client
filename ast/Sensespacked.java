package ast;

import world.Critter;

public class Sensespacked {
	public static int sniff(Critter c, Sensespace s){
		switch (s.pres){
		case ahead:
			return spacesahead(c,s.only.value(c));
		case nearby:
			return nearby(c,s.only.value(c));
		case random:
			return rando(c,s.only.value(c));
		default:
			return 0;
		}
	}
	
	/** returns the value of the hex ahead according to the spec
	 * Not quite sure what to do if ahead isn't on the map. Will look it up TODO
	 *TODO make sure it handles negative aheads too.
	 * @param c
	 * @param where
	 * @return
	 */
	private static int spacesahead(Critter c, int where){
		if (where == 0){
			return c.getNumRep();
		}
		else{
			return pseudomove(c,where);
		}
	}
	
	/** It moves the critter without actually expending energy, then it uses nearby to sense
	 * and finally it moves the critter back. Note, this should only be used for positive values of where
	 * The critter may not examine itself using pseudomove.
	 */
	private static int pseudomove(Critter c, int where) {
		int [] coords = new int [] {c.row, c.column};
		for (int place = 0; place < where - 1; place ++){
			int [] newplace = Actionpacked.dircoords(c,true);
			c.row = newplace[0];
			c.column = newplace[1];
		}
		int returned = nearby(c, c.direction);
		c.row = coords[0]; c.column = coords[1];
		return returned;
		
	}
	
	/**returns the information on the specified hex
	 * 
	 * @param c
	 * @param direction
	 * @return
	 */
	private static int nearby(Critter c, int direction) {
		int remember = c.direction;
		c.direction = direction;
		int then = c.w.getNumRep(Actionpacked.dircoords(c,true));
		c.direction = remember;
		return then;
	}
	
	
	/** implements the random according to the spec.*/
	private static int rando(Critter c, int bound) {
		return bound < 2 ? 0 : c.r.nextInt(bound);
	}

}
