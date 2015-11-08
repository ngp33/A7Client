package gui;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Shape;
import world.World;

public abstract class Layer {
	protected AnchorPane leftright;
	protected AnchorPane general;
	protected double xcoord;
	protected double ycoord;
	protected double xjust;
	protected double yjust;
	Double rtthr = 1.732050808;

	public Layer(Group g, double xcoord, double ycoord) {
		general = new AnchorPane();
		leftright = new AnchorPane();
		general.getChildren().add(leftright);
		g.getChildren().add(general);
		this.xcoord = xcoord;
		this.ycoord = ycoord;
	}
	
	public void shiftTransverse (double deltaxjust, double deltayjust) {
		xjust += deltaxjust;
		yjust += deltayjust;
		AnchorPane.setLeftAnchor(leftright, xjust);
		AnchorPane.setTopAnchor(leftright, yjust);
	}
	
	protected abstract void resize(World w);
	

	
	protected void place(Shape p, double [] posit) {
		AnchorPane.setLeftAnchor(p, posit[0]);
		AnchorPane.setTopAnchor(p, posit[1]);
	}
	
	/**Calculates the size of the hexagons based on the size of the anchorpane*/
	protected double getsize(World w) {
		int [] a = w.worlddim();
		double ysize = (ycoord - 23) / (rtthr * (a[0] + .5)); //The 23 is pretty arbitrary here too
		double xsize = (xcoord / (a[1] * 1.5 + .5));
		return ysize < xsize ? ysize : xsize;
	}
	
	/**returns the total number of pixel things that the entire hexagonal pattern
	 * occupies.
	 * @return
	 */
	public double xspace(World w) {
		int [] a = w.worlddim();
		double size = (getsize(w) * (1 + .5)) * a[1]; //getsize * 1.5 = xdist of a hex
		size += .5 * getsize(w);
		return size;
	}
	
	public double yspace(World w) {
		int [] a = w.worlddim();
		double size = (getsize(w) * rtthr) * a[0] + 23; // getsize * rtthr * 2/2 is the yheight
		size += getsize(w) * rtthr / 2;
		System.out.println(size);
		return size;
	}
	
	/**Changes the scale by both adjusting the anchorpane size and re-justifying the
	 * anchorpane within the greater anchorpane.
	 * @param amount
	 * @param a
	 */
	public void zoom (double amount, World w) {
		this.xcoord += amount;
		this.ycoord += amount;
		xjust -= amount / 2;
		yjust -= amount / 2;
		resize(w);
		shiftTransverse(-amount / 2, - amount/ 2);
	}

}
