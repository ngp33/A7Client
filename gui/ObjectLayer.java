package gui;

import javafx.scene.Group;
import world.World;

import java.util.Hashtable;

public class ObjectLayer extends Layer {
	Hashtable< Integer [], Inhabitant> objects = new Hashtable< Integer[], Inhabitant> ();

	public ObjectLayer(Group g, double xcoord, double ycoord) {
		super(g, xcoord, ycoord);
	}

	@Override
	protected void resize(World w) {
		// TODO Auto-generated method stub
		
	}
	
	/**This adds an inhabitant to the list. It should be called whenever a new inhabitant is made*/
	public void addInhabitant(Inhabitant i) {
		objects.put(new Integer [] {i.row, i.col}, i);
	}
	
	/**This is run whenever an update to the world is made to make sure that all the inhabitants
	 * are Still valid. If an inhabitant is invalid, it is deleted?*/
	public void checkInhabitant(Hexagon h, World w) {
		int num = w.getNumRep(new int [] {h.row + (h.col + 1)/2, h.col}); //This is a dangerous game
		Inhabitant i = objects.get(new int [] {h.row + (h.col + 1)/2, h.col});//dealing with mapped and unmapped positions
		//Be careful and clarify it.
		Integer othernum = i == null ? null : i.getNumRep();
		if (othernum == null || i.erased()) {
			if (num != 0) {
				addInhabitant(makeRightInhabitant(h,w));
			}
		}
	}
	
	
	private Inhabitant makeRightInhabitant(Hexagon h,World w) {
		int num = w.getNumRep(new int [] {h.row + (h.col + 1) / 2, h.col}); //Dangerous game again...
		if (num == w.ROCK_VALUE) {
			return new RockGraphic(h,w, leftright);
		}
		else if (num < w.ROCK_VALUE) {
			return new FoodGraphic(h, w, leftright);
		}
		else if (num > 0){
			return new CritterGraphic(h,w, leftright);
		}
		else {
			return null;
		}
	}

}
