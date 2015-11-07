package gui;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import world.World;

public class RockGraphic extends Inhabitant {
	
	Rectangle p;
	private double rtthr = 1.732050808;

	public RockGraphic(World world, int row, int col, double size, AnchorPane a, double[] position) {
		super(world, row, col, size, a, position);
		p = new Rectangle();
		sizeupdate(size, position);
		a.getChildren().add(p);
	}

	@Override
	public void sizeupdate(double size, double [] position) {
		posit = position;
		posit[0] += size / 4;
		posit[1] += size * rtthr/ 2;
		AnchorPane.setLeftAnchor(p, position[0]);
		AnchorPane.setTopAnchor(p, position[1]);
		p.setWidth(size * 3 / 4);
		p.setHeight(size * rtthr / 4);
		this.size = size;
	}


}
