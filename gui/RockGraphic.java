package gui;

import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import world.World;

public class RockGraphic extends Inhabitant {
	
	//Rectangle p;
	CircleImage ci;
	private double rtthr = 1.732050808;
	private double rttwo = 1.414213562;

	public RockGraphic(Hexagon h, World w, AnchorPane parent, Controller c, ObjectLayer objectLayer) {
		super(h,w, parent, c, objectLayer);
		ci = new CircleImage(new Image("Rock.png"));
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
	public void update() {
	}
	
	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg);
		contr.onHexClicked(row, col);
	}


}
