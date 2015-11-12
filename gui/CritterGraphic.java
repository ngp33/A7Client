package gui;

import java.util.Observable;

import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import world.Critter;
import world.World;

public class CritterGraphic extends Inhabitant {

	double scale;
	Critter c;
	World w;
	double size;

	public CritterGraphic(Hexagon h, World w, AnchorPane a, Controller k, ObjectLayer ol) {
		super (h,w,a, k, ol);
		this.w = w;
		c = (Critter) w.getHex(h.row, h.col);
		ci = new CircleImage(new Image("critter.png"));
		ci.add(a);
		ci.addObserver(this);
		ci.rotate(60 * c.direction);
		sizeupdate(h.size, h.position);
		colorUpdate();
		
	}

	@Override
	public void sizeupdate(double size, double [] position) {
		this.size = size;
		scale = size * (2 + Math.log10(c.mem[3]))/3;//some modifier for the size based on the critters size.
		posit[0] = position[0] + size - scale / rttwo;
		posit[1] = position[1] + size * rtthr / 2 - scale /rttwo;
		ci.setSize(scale);
		ci.setAnchors(posit);
		
	}
	
	public void colorUpdate() {
		ci.color(energyRatio());
	}
	
	private double energyRatio() {
		return c.mem[4] / (((double) c.mem[3]) * w.ENERGY_PER_SIZE);
	}

	@Override
	public void update() {
		sizeupdate(size, h.position); 
		colorUpdate();
		directionUpdate();
	}

	private void directionUpdate() {
		ci.rotate(c.direction * 60);
	}
	
	public void normal() {
		ci.normal();
	}
	
	public void chosen() {
		ci.chosen();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		super.update(o, arg);
		ol.unique = c;
		ci.chosen();
		contr.onHexClicked(row, col);
	}

}
