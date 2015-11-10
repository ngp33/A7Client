package gui;

import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import world.World;

public class Hexgrid extends Layer {
	
	private Hexagon [] hexes;
	private ObjectLayer Ol;
	
	public Hexgrid(Group g, double xcoord, double ycoord) {
		super(g, xcoord, ycoord);
		Ol = new ObjectLayer(g, xcoord, ycoord);
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
				place(temp, posit);
				place ++;
			}
		}
	}
	
	/**Run whenever the worldhanger is notified of a change. Should check that each hex
	 * is properly represented and add to the object layer if its not.*/
	public void objectUpdate(World w) {
		Ol.updateDelete(w);
		for (Hexagon them : hexes) {
			Ol.checkInhabitant(them, w);
		}
	}
	
	@Override
	public void zoom(double amount, World w) {
		this.xcoord += amount; //Though this code looks like the code in shiftTransverse, dynamic dispatch
		this.ycoord += amount;//means that calling shifttransverse here would call it on Ol meaning
		xjust -= amount / 2;//Ol would be shifted twice which would be bad. Hence, I rewrote the code
		yjust -= amount / 2; 
		AnchorPane.setLeftAnchor(leftright, xjust);
		AnchorPane.setTopAnchor(leftright, yjust);
		resize(w);
		Ol.zoom(amount, w);
	}
	
	public void shiftTransverse(double dx, double dy) {
		super.shiftTransverse(dx, dy);
		Ol.shiftTransverse(dx, dy);
	}
	
	public void center(World w) {
		double dx = (xcoord - xspace(w)) / 2;
		double dy = (ycoord - yspace(w)) / 2;
		shiftTransverse(dx, dy);
	}

}
