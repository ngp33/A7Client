package gui;

import javafx.scene.Group;
import javafx.scene.layout.HBox;
import world.World;

import java.util.Collection;
import java.util.Hashtable;

public class ObjectLayer extends Layer {
	Hashtable< String, Inhabitant> objects = new Hashtable< String, Inhabitant> (); //A hashtable (as usual
	//seemed the best choice here because of all the searching. Strings are used (where the string is row.tostring
	//col.tostring) because arrays are tricky with equality, but I know strings work with it. Row,col is safe because
	//no two hexes could possibly have the same row,col

	public ObjectLayer(Group g, double xcoord, double ycoord) {
		super(g, xcoord, ycoord);
		//sp.setOpacity(0.0);
		leftright.setPrefWidth(xcoord);
		leftright.setPrefHeight(ycoord);
	}

	@Override
	protected void resize(World w) {
		Collection<Inhabitant> c = objects.values();
		Inhabitant [] values = new Inhabitant [c.size()];
		c.toArray(values);
		double s = getsize(w);
		for (Inhabitant o : values) {
			o.sizeupdate(s, getplace(o.row - (o.col + 1 )/ 2, o.col, s)); //Get place works in grid
			//o.sizeupdate(s);
		} //coordinate system, so I have to switch row back here.
		
	}
	
	/**This adds an inhabitant to the list. It should be called whenever a new inhabitant is made*/
	public void addInhabitant(Inhabitant i) {
		objects.put(Integer.toString(i.row) + Integer.toString(i.col), i);
	}
	
	/**This is run whenever an update to the world is made to make sure that all the inhabitants
	 * are Still valid. If an inhabitant is invalid, it is deleted?*/
	public void checkInhabitant(Hexagon h, World w) {
		int num = w.getNumRep(new int [] {h.row, h.col});
		Inhabitant i = objects.get(Integer.toString(h.row) + Integer.toString(h.col));
		Integer othernum = i == null ? null : i.getNumRep();
		if (othernum == null) {
			if (num != 0) {
				addInhabitant(makeRightInhabitant(h,w));
			}
		}
		resize(w); //This line shouldn't be necessary, but it at least seems to erase
		//the symptoms of a bug. Come back to this if you have time. The problem seems to be that the
		//hex position for the food-graphic's constructor is broken. Not sure how/why.
	}
	
	
	private Inhabitant makeRightInhabitant(Hexagon h,World w) {
		int num = w.getNumRep(new int [] {h.row, h.col});
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
	
	public void updateDelete(World w) {
		Collection<Inhabitant> c = objects.values();
		Inhabitant [] values = new Inhabitant [c.size()];
		c.toArray(values);
		for (Inhabitant o : values) {
			if (o.erased()) {
				objects.remove(Integer.toString(o.row) + Integer.toString(o.col));
			}
			else {
				o.update();
			}
		}
	}

}
