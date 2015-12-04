package clientRequestHandler;

import java.util.Collection;

import ast.Program;
import gui.Controller;
import world.Critter;
import world.Food;
import world.Hex;
import world.Rock;
import world.World;

/**Creates a bundle factory which bundles information into specific classes*/
public class BundleFactory {
	
	public BundleFactory() {
		
	}
	
	//Used in getting the critterList I assume
	public static class CritListBundle {
		Inhabitant [] cb;
	}
	
	//probably not necessary. An inhabitant constructor could probably do the same
	/*
	private class CritBundle extends Inhabitant {
		public CritBundle(int critID) {
			//TODO form the bundle based on the critter ID.
			Critter c= w.critters.get(critID);
			id = critID;
			row = c.row;
			col = c.col;
			species_id = c.name;
			direction = c.direction;
			mem = c.mem;
			String [] str = c.genes.toString().split("\n");
			recently_executed_rule = c.mostrecentruleplace;
		}
	}*/
	
	/**Will be used in getting the world I assume...*/
	public static class WorldBundle {
		int current_timestep;
		int current_version_number;
		//int update_since not really sure what to do about this.
		float rate;
		String name;
		int population;
		int rows;
		int cols;
		int [] dead_critters;
		Inhabitant [] state;
	}
	
	/**A general class for the inhabitants. It has all the fields
	 * that any inhabitant would need, so all inhabitants can be
	 * unpacked using this class. Used so far in create_entity among other things*/
	public static class Inhabitant {
		int row;
		int col;
		String type;
		int id;
		String species_id;
		int direction;
		int [] mem;
		int value;
		int recently_executed_rule;
		String program; //should this be a string?
		int amount;
		
		public Inhabitant(Rock r) {
			row = r.row;
			col = r.col;
			type = "rock";
		}
		
		public Inhabitant(Food f) {
			row = f.row;
			col = f.col;
			type = "food";
			amount = f.getQuantity();
		}
	}
	
	/**A bundle of critters which is used in the Post method*/
	public static class CritPlacementBundle {
		String species_id;
		String program;
		int [] mem;
		Placement [] positions;
		int num;
		private CritPlacementBundle(Critter c) {
			species_id = c.name;
			program = c.genes.toString();
			mem = c.mem;
		}
		/**Makes a placementbundle where location of placement is specified for each critter*/
		public CritPlacementBundle(Critter c, Placement [] pos) {
			this(c);
			positions = pos;
		}
		/**Makes a placementbundle where there is a specified number of randomly placed crits*/
		public CritPlacementBundle(Critter c, int number) {
			this(c);
			num = number;
		}
		
	}
	
	public static class Placement {
		int row;
		int col;
	}
	
	public static class SpeciesAndIDs {
		String species_id;
		int[] ids;
	}
	
}
