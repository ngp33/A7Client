package gui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import world.Critter;
import world.World;

import java.util.Collection;
import java.util.Hashtable;

public class ObjectLayer extends Layer {
	double size;
	Hashtable< String, Inhabitant> objects = new Hashtable< String, Inhabitant> (); //A hashtable (as usual
	//seemed the best choice here because of all the searching. Strings are used (where the string is row.tostring
	//col.tostring) because arrays are tricky with equality, but I know strings work with it. Row,col is safe because
	//no two hexes could possibly have the same row,col
	public Critter unique; //selected critter

	public ObjectLayer(Group g, double xcoord, double ycoord, World w, Controller c) {
		super(g, xcoord, ycoord, w, c);
		size = getsize();
		leftright.setPickOnBounds(false);


	}

	@Override
	protected void resize() {
		Collection<Inhabitant> c = objects.values();
		Inhabitant [] values = new Inhabitant [c.size()];
		c.toArray(values);
		double s = getsize();
		for (Inhabitant o : values) {
			o.sizeupdate(s, getplace(o.row - (o.col + 1 )/ 2, o.col, s)); //Get place works in grid
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
	}
	
	
	private Inhabitant makeRightInhabitant(Hexagon h,World w) {
		int num = w.getNumRep(new int [] {h.row, h.col});
		if (num == w.ROCK_VALUE) {
			return new RockGraphic(h,w, leftright, c, this);
		}
		else if (num < w.ROCK_VALUE) {
			return new FoodGraphic(h, w, leftright, c, this);
		}
		else if (num > 0){
			CritterGraphic cg = new CritterGraphic(h,w, leftright, c, this);
			hueCheck(cg);
			return cg;
		}
		else {
			return null;
		}
	}
	
	public void updateDelete() {
		Collection<Inhabitant> c = objects.values();
		Inhabitant [] values = new Inhabitant [c.size()];
		c.toArray(values);
		for (Inhabitant o : values) {
			if (o.erased()) {
				objects.remove(Integer.toString(o.row) + Integer.toString(o.col));
			}
			else {
				o.update();
				if (o.getNumRep() > 0) {
					hueCheck((CritterGraphic) o);
				}
			}
		}
	}

	public void deselect() {
		if (unique != null) {
			if (unique == w.getHex(unique.row, unique.col)) {
				CritterGraphic cg = (CritterGraphic) objects.get(Integer.toString(unique.row) + Integer.toString(unique.col));
				if (cg != null) {
					cg.normal();
					unique = null;
				}
			}
		}
	}
	
	public void hueCheck(CritterGraphic cg) {
		if (unique != null) {
			if (unique == w.getHex(unique.row, unique.col)) {
				if (cg.row == unique.row && cg.col == unique.col) {
					cg.chosen();
				}
			}
		}
		
	}
	
	public boolean CritterEqual(int row, int col) {
		return unique == null ? false : unique.row == row && unique.col == col;
	}

}
