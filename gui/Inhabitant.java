package gui;

import javafx.scene.layout.AnchorPane;
import world.World;

public abstract class Inhabitant {
	World w;
	int row;
	int col;
	double size;
	int numrep;
	double [] posit;
	AnchorPane a;
	
	public Inhabitant (World world, int row, int col, double size, AnchorPane a, double [] position) {
		w = world;
		this.row = row;
		this.col = col;
		this.size = size;
		numrep = w.getNumRep(new int [] {row, col});
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
	protected abstract void sizeupdate(double size, double [] position);
	
	

}
