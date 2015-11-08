package gui;

import javafx.scene.Group;
import world.World;

public class Hexgrid extends Layer {
	
	private Hexagon [] hexes;
	private ObjectLayer Ol;
	
	public Hexgrid(Group g, double xcoord, double ycoord, Hexagon [] h) {
		super(g, xcoord, ycoord);
		Ol = new ObjectLayer(g, xcoord, ycoord);
		hexes = h;
		// TODO Auto-generated constructor stub
	}
	
	public void setHexGrid(Hexagon [] h) {
		hexes = h;
		for (Hexagon them : hexes) {
			leftright.getChildren().add(them);
		}
	}



	@Override
	protected void resize(World w) {
		int [] a = w.worlddim();
		int place = 0;
		for (int row = 0; row < a[0]; row++) {
			for (int col = 0; col < a[1]; col ++) {
				double size = getsize(w);
				double [] posit = getplace(row,col,size);
				Hexagon temp = hexes[place];
				temp.position = posit;
				temp.resize(size);
				//temp.getInhabitant(w, anch); TODO put the line back
				place(temp, posit);
				place ++;
			}
		}
	}
	
	private double [] getplace(int row, int col, double size) {
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
	
	/**Run whenever the worldhanger is notified of a change. Should check that each hex
	 * is properly represented and add to the object layer if its not.*/
	public void objectUpdate(World w) {
		for (Hexagon them : hexes) {
			Ol.checkInhabitant(them, w);
		}
	}

}
