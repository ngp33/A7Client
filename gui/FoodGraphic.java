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
		sizeupdate(h.size, h.position);
		//System.out.println(h.position[0]);
		//System.out.println(h.position[1]);
	}

	@Override
	public void sizeupdate(double size, double [] position) {
		//System.out.println(h.position[0]);
		//System.out.println(h.position[1]);
		position[0] += size - size / rttwo;
		position[1] += size * rtthr / 2 - size / rttwo;
		ci.setSize(size);
		ci.setAnchors(position);
		
	}

	@Override
	public void update() { //Code could be modified so that update actually alters the food quantity
		//rather than creating new food for each update.
	}

}
