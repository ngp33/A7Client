package gui;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import world.Critter;
import world.World;

public class CritterGraphic extends Inhabitant {

	double scale;
	Critter c;

	public CritterGraphic(Hexagon h, World w, AnchorPane a) {
		super (h,w,a);
		c = (Critter) w.getHex(h.row, h.col);
		ci = new CircleImage(new Image("critter.png"));
		ci.add(a);
		ci.rotate(60 * c.direction);
		sizeupdate(h.size, h.position);
	}

	@Override
	public void sizeupdate(double size, double [] position) {
		scale = size * (2 + Math.log10(c.mem[3]))/3;//some modifier for the size based on the critters size.
		position[0] += size - scale / rttwo;
		position[1] += size * rtthr / 2 - scale / rttwo;
		ci.setSize(scale);
		ci.setAnchors(position);
		
		
	}
}
