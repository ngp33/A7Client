package gui;

import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import world.World;

public class Hexgrid extends Layer {
	
	private Hexagon [] hexes;
	private ObjectLayer Ol;
	ScrollPane sp;
	double hcur;
	double vcur;
	
	public Hexgrid(ScrollPane scr, Group g, double xcoord, double ycoord) {
		super(g, xcoord, ycoord);
		Ol = new ObjectLayer(g, xcoord, ycoord);
		sp = scr;
		
		sp.setContent(g);
		hcur = .5;
		vcur = .5;
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
		xcoord += amount;
		ycoord += amount;
		//Ol would be shifted twice which would be bad. Hence, I rewrote the code
		//super.shiftTransverse(-amount/2, -amount/2); //super method called so dynamic dispatch doesn't call
		//shiftTransverse(-amount/2, -amount/2);
		resize(w); //shiftTransverse on Ol twice
		Ol.zoom(amount, w);
		sp.setHvalue(hcur);
		sp.setVvalue(vcur);
		
	}
	
	public void shiftTransverse(double dx, double dy) {
		double temph = hcur + dx/xcoord;
		if (temph < 1 &&  temph > 0) {
			hcur = temph;
		}
		double temv = vcur + dy/ycoord;
		if (temv < 1 && temv > 0) {
			vcur = temv;
		}
		sp.setHvalue(hcur);
		sp.setVvalue(vcur);
		//super.shiftTransverse(dx, dy);
		//Ol.shiftTransverse(dx, dy);
	}
	
	/**Centers the grids in the group with either the xspace or yspace as the limiting factor*/
	public void center(World w) {
		double dx = (xcoord - xspace(w)) / 2;
		double dy = (ycoord - yspace(w)) / 2;
		xcoord = xspace(w);
		ycoord = yspace(w);//Ycoord is important for the getplace method (xcoord probably needn't have been
		Ol.xcoord = xspace(w);//updated here) so we have to adjust the ycoord so that it's the appropriate size
		Ol.ycoord = yspace(w);
		resize(w);
		shiftTransverse(dx, dy);
	}
}
