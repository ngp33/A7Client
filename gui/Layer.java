package gui;

import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Shape;
import world.World;

public abstract class Layer {
	protected AnchorPane leftright;
	//protected AnchorPane general;
	protected ScrollPane sp;
	protected double xcoord;
	protected double ycoord;
	protected double xjust;
	protected double yjust;
	Double rtthr = 1.732050808;
	protected World w;

	public Layer(Group g, double xcoord, double ycoord, World w) {
		//general = new AnchorPane();
		//sp = new ScrollPane();
		leftright = new AnchorPane();
		//general.getChildren().add(leftright);
		//sp.setContent(leftright);
		//g.getChildren().add(general);
		//g.getChildren().add(sp);
		leftright.setPrefWidth(xcoord);
		leftright.setPrefHeight(ycoord);
		g.getChildren().add(leftright);
		this.xcoord = xcoord;
		this.ycoord = ycoord;
		this.xjust = 0;
		this.yjust = 0;
		this.w = w;
	}
	
	/*public void shiftTransverse (double deltaxjust, double deltayjust) {
		xjust += deltaxjust;
		yjust += deltayjust;
		AnchorPane.setLeftAnchor(leftright, xjust);
		AnchorPane.setTopAnchor(leftright, yjust);
		//general.setHvalue(xjust);
		//general.setVvalue(yjust);
	}*/
	
	protected abstract void resize();
	

	
	protected void place(Shape p, double [] posit) {
		AnchorPane.setLeftAnchor(p, posit[0]);
		AnchorPane.setTopAnchor(p, posit[1]);
	}
	
	/**Calculates the size of the hexagons based on the size of the anchorpane*/
	protected double getsize() {
		int [] a = w.worlddim();
		double ysize = (ycoord - 23) / (rtthr * (a[0] + .5)); //The 23 is pretty arbitrary here too
		double xsize = (xcoord / (a[1] * 1.5 + .5));
		return ysize < xsize ? ysize : xsize;
	}
	
	/**returns the total number of pixel things that the entire hexagonal pattern
	 * occupies.
	 * @return
	 */
	public double xspace() {
		int [] a = w.worlddim();
		double size = (getsize() * (1 + .5)) * a[1]; //getsize * 1.5 = xdist of a hex
		size += .5 * getsize();
		return size;
	}
	
	public double yspace() {
		int [] a = w.worlddim();
		double size = (getsize() * rtthr) * a[0] + 23; // getsize * rtthr * 2/2 is the yheight
		size += getsize() * rtthr / 2;

		return size;
	}
	
	/**Changes the scale by both adjusting the anchorpane size and re-justifying the
	 * anchorpane within the greater anchorpane.
	 * @param amount
	 * @param a
	 */
	public void zoom (double amount, World w) {
		xcoord += amount;
		ycoord += amount;
		//shiftTransverse(-amount/2, -amount/2);
		resize();
	}
	
	/**Gets the place of the object using the grid coordinate system (not the critter coordinate
	 * system)
	 * @param row
	 * @param col
	 * @param size
	 * @return
	 */
	public double [] getplace(int row, int col, double size) {
		double xco = col * ( size + size / 2);
		double yco = row * (size * rtthr);
		yco += col % 2 == 1 ? size * rtthr / 2 : 0;
		//yco represents the distance from the bottom, so to get the distance from the top
		//we have to subtract it, and the distance from the bottom to the top of the hex
		//(size * rtthr) from ycoord.
		return new double [] {xco, ycoord - yco - size * rtthr - 23};
		//TODO I have no idea why the above doesn't work without the arbitrary 20,
		//but it doesnt...
	}
	
	public int [] reverseGetPlace(double x, double y, double size) {
		int col = (int) (x/(size + size / 2));
		int row = (int) ((ycoord - 23 - y - (col % 2 == 1 ? size*rtthr/2 : 0)  )/(size * rtthr));
		row += (col + 1)/2;
		return new int [] {row,col};
	}

}
