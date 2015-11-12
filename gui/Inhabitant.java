package gui;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.layout.AnchorPane;
import world.World;

public abstract class Inhabitant implements Observer {
	World w;
	int row; //Row from the critter world
	int col; //Col from the critter world
	private int numrep;
	AnchorPane a;
	Hexagon h;
	CircleImage ci;
	double rttwo = 1.414213562;
	double rtthr = 1.732050808;
	protected double [] posit;
	protected Controller contr;
	protected boolean selected;
	protected ObjectLayer ol;

	public Inhabitant (Hexagon h, World w, AnchorPane a, Controller c, ObjectLayer ol) {
		this.h = h;
		this.w = w;
		this.row = h.row;
		this.col = h.col;
		numrep = w.getNumRep(new int [] {row, col});
		this.a = a;
		posit = new double [2];
		this.contr = c;
		this.ol = ol;
	}
	
	/**Decides whether an object should be deleted, and does so if it should be*/
	public boolean erased () {
		if (w.getNumRep(new int [] {row, col}) != numrep) {
			ci.remove(a);
			return true;
		}
		return false;
	}
	

	/**Updates the size of the inhabitant based on the new size of the hex it's in. <br>
	 * Invariant: the inhabitant size instance variable should get size at the end.
	 * @param size
	 * 
	 * @param position
	 * 		The position of the hex (the idea is that with this information, it should be easy
	 * to identify the position of the object. Note that this position corresponds to the distance
	 * from the left part of the anchor, and the distance from the top.
	 */
	public abstract void sizeupdate(double size, double [] position);
	
	public int getNumRep() {
		return numrep;
	}
	
	public abstract void update();
	
	@Override
	public void update(Observable o, Object arg) {
		ol.deselect();
	}


}
