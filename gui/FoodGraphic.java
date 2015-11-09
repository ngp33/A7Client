package gui;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import world.Food;
import world.World;

public class FoodGraphic extends Inhabitant {
	Food f;

	public FoodGraphic(Hexagon h, World w, AnchorPane a) {
		super(h,w, a);
		f = (Food) w.getHex(h.row, h.col);
		ci = new CircleImage(new Image("Nuclear_critter_gunk.png"));
		ci.add(a);
		sizeupdate(h.size,h.position);
	}

	@Override
	public void sizeupdate(double size, double [] position) {
		position[0] += size - size / rttwo;
		position[1] += size * rtthr / 2 - size / rttwo;
		ci.setSize(size);
		ci.setAnchors(position);
		
	}

}
