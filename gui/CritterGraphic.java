package gui;

import javafx.scene.layout.AnchorPane;
import world.World;

public class CritterGraphic extends Inhabitant {
	public CritterGraphic (World w, int row, int col, double size, AnchorPane a, double [] p) {
		super(w, col, col, size, a, p);
	}

	@Override
	public void sizeupdate(double size, double [] position) {
		// TODO Auto-generated method stub
		
	}

}
