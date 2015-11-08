package gui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import world.World;

public class RockGraphic extends Inhabitant {
	
	Rectangle p;
	private double rtthr = 1.732050808;

	public RockGraphic(World world, int row, int col, double size, AnchorPane a, double[] position) {
		super(world, row, col, size, position);
		p = new Rectangle();
		sizeupdate(size, position);
		a.getChildren().add(p);
	}

	public RockGraphic(Hexagon h, World w, AnchorPane parent) {
		super(h,w, parent);
		p = new Rectangle();
		parent.getChildren().add(p);
		sizeupdate(size, h.position);
	}

	@Override
	public void sizeupdate(double size, double [] position) {
		posit = position;
		posit[0] += size / 4;
		posit[1] += size * rtthr/ 4;
		AnchorPane.setLeftAnchor(p, posit[0]);
		AnchorPane.setTopAnchor(p, posit[1]);
		p.setWidth(size * 3 / 2);
		p.setHeight(size * rtthr / 2);
		this.size = size;
	}


}
