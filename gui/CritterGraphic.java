package gui;

import javafx.scene.layout.AnchorPane;
import world.World;

public class CritterGraphic extends Inhabitant {
	public CritterGraphic (World w, int row, int col, double size, AnchorPane a, double [] p) {
		super(w, col, col, size, p);
	}

	public CritterGraphic(Hexagon h, World w, AnchorPane a) {
		super (h,w,a);
		//Add something to make the anchorpane work (so that the graphics are a child of the anchorpane)
	}

	@Override
	public void sizeupdate(double size, double [] position) {
		// TODO Auto-generated method stub
		
	}

	
	

}
