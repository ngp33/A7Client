package gui;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import world.World;

public class RockGraphic extends Inhabitant {
	
	//Rectangle p;
	CircleImage ci;
	private double rtthr = 1.732050808;
	private double rttwo = 1.414213562;

	public RockGraphic(Hexagon h, World w, AnchorPane parent) {
		super(h,w, parent);
		ci = new CircleImage(new Image("Rock.png"));
		ci.add(a);
		sizeupdate(size, h.position);
	}

	@Override
	public void sizeupdate(double size, double [] position) {
		posit = position;
		posit[0] += size - size / rttwo;
		posit[1] += size * rtthr / 2 - size / rttwo;
		ci.setSize(size);
		ci.setAnchors(position);
	}


}
