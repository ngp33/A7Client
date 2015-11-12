package gui;

import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import world.Food;
import world.World;

public class FoodGraphic extends Inhabitant {
	Food f;

	public FoodGraphic(Hexagon h, World w, AnchorPane a, Controller c, ObjectLayer ol) {
		super(h,w, a, c, ol);
		f = (Food) w.getHex(h.row, h.col);
		ci = new CircleImage(new Image("Nuclear_critter_gunk.png"));
		ci.add(a);
		ci.addObserver(this);
		sizeupdate(h.size, h.position);
	}

	@Override
	public void sizeupdate(double size, double [] position) {
		posit[0] = position[0] + size - size / rttwo;
		posit[1] = position[1] + size * rtthr / 2 - size /rttwo;
		ci.setSize(size);
		ci.setAnchors(posit);
		
	}

	@Override
	public void update() { //Code could be modified so that update actually alters the food quantity
		//rather than creating new food for each update.
	}
	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg);
		contr.onHexClicked(row, col);
	}


}
