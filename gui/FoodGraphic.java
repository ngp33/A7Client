package gui;

import javafx.scene.layout.AnchorPane;
import world.World;

public class FoodGraphic extends Inhabitant {

	public FoodGraphic(World world, int row, int col, double size, AnchorPane a, double[] position) {
		super(world, row, col, size, position);
		// TODO Auto-generated constructor stub
	}

	public FoodGraphic(Hexagon h, World w, AnchorPane a) {
		super(h,w, a);
		//Add something to make the food work in anchorpane a;
	}

	@Override
	public void sizeupdate(double size, double [] position) {
		// TODO Auto-generated method stub
		
	}

}
