package gui;

import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import world.Critter;
import world.World;

public class Hexgrid extends Layer {
	
	private Hexagon [] hexes;
	private ObjectLayer Ol;
	ScrollPane sp;
	double hcur;
	double vcur;

	
	public Hexgrid(ScrollPane scr, Group g, double xcoord, double ycoord, World w, Controller c) {
		super(g, xcoord, ycoord, w, c);
		Ol = new ObjectLayer(g, xcoord, ycoord, w, c);
		sp = scr;
		g.prefWidth(xcoord);
		g.prefHeight(ycoord);
		sp.setContent(g);
		hcur = .5;
		vcur = .5;
	}
	
	public void setHexGrid(Hexagon [] h) {
		hexes = h;
		for (Hexagon them : hexes) {
			leftright.getChildren().add(them);
			them.ol = Ol;
		}
	}



	@Override
	protected void resize() {
		int [] a = w.worlddim();
		int place = 0;
		double size = getsize();
		for (int row = 0; row < a[0]; row++) {
			for (int col = 0; col < a[1]; col ++) {
				double [] posit = getplace(row,col,size);
				Hexagon temp = hexes[place];
				temp.position = posit;
				temp.resize(size);
				place(temp, posit);
				place ++;
			}
		}
		sp.setHvalue(hcur);
		sp.setVvalue(vcur);
	}
	
	/**Run whenever the worldhanger is notified of a change. Should check that each hex
	 * is properly represented and add to the object layer if its not.*/
	public void objectUpdate() {
		Ol.updateDelete();
		for (Hexagon them : hexes) {
			Ol.checkInhabitant(them, w);
		}
		Ol.resize(); //this shouldn't be necessary, but its de-symptomizing a bug. Horrible form.
		sp.setHvalue(hcur); //For some reason the scroll bars adjust when something new is added (maybe because
		sp.setVvalue(vcur);//the anchorpane bounds change?) so I put this here to bring the scroll bar
		//back to its original position
	}
	
	@Override
	public void zoom(double amount, World w) {
		super.zoom(amount, w);
		Ol.zoom(amount, w);
		sp.setHvalue(hcur);
		sp.setVvalue(vcur);
		
	}
	
	public void shiftTransverse(double dx, double dy) {
		double temph = hcur + dx/xcoord;
		if (temph < 1 &&  temph > 0) {
			hcur = temph;
		}
		else if (temph >= 1) {
			hcur = 1;
		}
		else {
			hcur = 0;
		}
		double temv = vcur + dy/ycoord;
		if (temv < 1 && temv > 0) {
			vcur = temv;
		}
		else if (temv >= 1) {
			vcur = 1;
		}
		else {
			vcur = 0;
		}
		sp.setHvalue(hcur);
		sp.setVvalue(vcur);
	}
	
	/**Centers the grids in the group with either the xspace or yspace as the limiting factor*/
	public void center(World w) {
		double dx = (xcoord - xspace()) / 2;
		double dy = (ycoord - yspace()) / 2;
		xcoord = xspace();
		ycoord = yspace();//Ycoord is important for the getplace method (xcoord probably needn't have been
		Ol.xcoord = xspace();//updated here) so we have to adjust the ycoord so that it's the appropriate size
		Ol.ycoord = yspace();
		resize();
		shiftTransverse(dx, dy);
	}


}
