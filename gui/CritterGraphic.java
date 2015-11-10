package gui;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import world.Critter;
import world.World;

public class CritterGraphic extends Inhabitant {

	double scale;
	Critter c;
	World w;
	double size;

	public CritterGraphic(Hexagon h, World w, AnchorPane a) {
		super (h,w,a);
		this.w = w;
		c = (Critter) w.getHex(h.row, h.col);
		ci = new CircleImage(new Image("critter.png"));
		ci.add(a);
		ci.rotate(60 * c.direction);
		sizeupdate(h.size, h.position);
		colorUpdate();
	}

	@Override
	public void sizeupdate(double size, double [] position) {
		this.size = size;
		scale = size * (2 + Math.log10(c.mem[3]))/3;//some modifier for the size based on the critters size.
		position[0] += size - scale / rttwo;
		position[1] += size * rtthr / 2 - scale / rttwo;
		ci.setSize(scale);
		ci.setAnchors(position);
		
	}
	
	public void colorUpdate() {
		ci.color(energyRatio());
	}
	
	private double energyRatio() {
		return c.mem[4] / (((double) c.mem[3]) * w.ENERGY_PER_SIZE);
	}

	@Override
	public void update() {
		sizeupdate(size, h.position); //Make sure the invariant keeps with size... it should...
		colorUpdate();
		directionUpdate();
	}

	private void directionUpdate() {
		ci.rotate(c.direction * 60);
		
	}
}
