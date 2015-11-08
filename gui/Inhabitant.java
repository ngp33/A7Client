package gui;

import javafx.scene.layout.AnchorPane;
import world.World;

public abstract class Inhabitant {
	World w;
	int row; //Row from the critter world
	int col; //Col from the critter world
	double size;
	private int numrep;
	double [] posit;
	AnchorPane a;
	Hexagon h;
	
	public Inhabitant (World world, int row, int col, double size, double [] position) {
		w = world;
		this.row = row;
		this.col = col;
		this.size = size;
		numrep = w.getNumRep(new int [] {row, col});
	}
	public Inhabitant (Hexagon h, World w, AnchorPane a) {
		this.h = h;
		this.w = w;
		this.row = h.row;
		this.col = h.col;
		this.size = h.size;
		numrep = w.getNumRep(new int [] {row, col});
		this.a = a;
	}
	
	/**Decides whether an object should be deleted, and does so if it should be*/
	public boolean erased () {
		if (w.getNumRep(new int [] {row, col}) != numrep) {
			a.getChildren().remove(this);
			return true;
		}
		return false;
	}
	

	/**Updates the size of the inhabitant based on the new size of the hex it's in. <br>
	 * Invariant: the inhabitant size instance variable should get size at the end.
	 * @param size
	 */
	public abstract void sizeupdate(double size, double [] position);
	
	public int getNumRep() {
		return numrep;
	}

}
