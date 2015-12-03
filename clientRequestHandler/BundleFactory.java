package clientRequestHandler;

import java.util.Collection;

import ast.Program;
import gui.Controller;
import world.Critter;
import world.World;

/**Creates a bundle factory which bundles information into specific classes*/
public class BundleFactory {
	
	public BundleFactory() {
		
	}
	
	public class critListBundle {
		//Linkedlist to hold the critters? //Nah it should be good
		critBundle [] cb; //this is probably incompatible with the info given in the API //Nah it should be good
	}
	
	public class critBundle extends inhabitants {
		
	}
	
	public class worldBundle {
		int current_timestep;
		int current_version_number;
		//int update_since not really sure what to do about this.
		float rate;
		String name;
		int population;
		int rows;
		int cols;
		int [] dead_critters;
		inhabitants [] state;
		public worldBundle(int rone, int rtwo, int cone, int ctwo, int timeinterval) {
			//TODO get world dif over this time
		}
	}
	
	/**A general class for the inhabitants. It has all the fields
	 * that any inhabitant would need, so all inhabitants can be
	 * unpacked using this class*/
	public class inhabitants {
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
	}
	
	public class critPlacementBundle {
		String species_id;
		String program;
		int [] mem;
		placement [] positions;
		int num;
		private critPlacementBundle(Critter c) {
			species_id = c.name;
			program = c.genes.toString();
			mem = c.mem;
		}
		/**Makes a placementbundle where location of placement is specified for each critter*/
		public critPlacementBundle(Critter c, placement [] pos) {
			this(c);
			positions = pos;
		}
		/**Makes a placementbundle where there is a specified number of randomly placed crits*/
		public critPlacementBundle(Critter c, int number) {
			this(c);
			num = number;
		}
		
	}
	
	public class placement {
		int row;
		int col;
	}
	

}
