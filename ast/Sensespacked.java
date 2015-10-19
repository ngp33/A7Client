package ast;

import world.Critter;

public class Sensespacked {
	public static int sniff(Critter c, Sensespace s){
		switch (s.pres){
		case ahead:
			break;
		case nearby:
			break;
		case random:
			break;
		default:
			break;
		}
	}
	
	/** returns the value of the hex ahead according to the spec
	 * Not quite sure what to do if ahead isn't on the map. Will look it up
	 * @param c
	 * @param where
	 * @return
	 */
	private static int spacesthere(Critter c, int where){
		//TODO
	}

}
