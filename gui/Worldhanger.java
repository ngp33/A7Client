package gui;

import java.util.Iterator;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import world.Rock;
import world.World;

//Problem: figure out how to order the inhabitant maker so that it makes inhabitants and updates them
//After the hexes all have positions.

public class Worldhanger extends Application {
	private World w = new World(7,5, "hi");
	private int xcoord = 400;//Only alter these (xcoord, ycoord) using scalechange
	private int ycoord = 400;
	private double xjust = 0.0;
	private double yjust = 0.0;
	private Hexagon [] hexes; //ultimately an array of hex graphic objects which is ordered
	//from 
	Double rtthr = 1.732050808;
	private Hexgrid h;
	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		//w.replace(new Rock(), w.getHex(4, 2));
		Group g = new Group();
		//AnchorPane b = new AnchorPane();
		//AnchorPane a = new AnchorPane();
		//g.getChildren().add(b);
		//b.getChildren().add(a);
		//AnchorPane.setLeftAnchor(a, xjust);
		//AnchorPane.setTopAnchor(a, yjust);
		Scene s = new Scene(g);
		primaryStage.setScene(s);
		primaryStage.setWidth(xcoord);
		primaryStage.setHeight(ycoord);
		h = new Hexgrid(g, xcoord, ycoord, hexes);
		//a.setPrefSize(xcoord, ycoord);
		//hexWorldMap(10.,a);
		//updateInhabitants(a);
		hexWorldMap(h.getsize(w));
		primaryStage.show();
		/*a.setOnMouseClicked(new EventHandler <Event>() {

			@Override //Anything that resizes also has to adjust xjust, yjust
			public void handle(Event event) {
				w.advance();
				updateInhabitants(a);
			}
			
		});*/
		
		s.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				handleKey(event.getCode());//, a);
				
			}
			
			
		});

	}
	
	private void handleKey(KeyCode keyCode) {//, AnchorPane a) {
		switch (keyCode.getName()) {
		case "A":
			//xjust += 3;
			h.shiftTransverse(3, 0);
			//reframe(a);
			break;
		case "S":
			//yjust -= 3;
			h.shiftTransverse(0, -3);
			//reframe(a);
			break;
		case "D":
			//xjust -= 3;
			//reframe(a);
			h.shiftTransverse(-3, 0);
			break;
		case "W":
			//yjust += 3;
			//reframe(a);
			h.shiftTransverse(0, 3);
			break;
		case "Up":
			//scalechange(3, a);
			//resize(a);
			h.zoom(3,w);
			break;
		case "Down":
			//scalechange(-3, a);
			//resize(a);
			h.zoom(-3, w);
			break;
		default:
			//System.out.println(keyCode.getName());
			break;
		}
	}
	
	/*public void drawhexes(AnchorPane ahh, int hexcircles, int[] center) {
		//Polygon p = new Hexagon(20.0);
		AnchorPane.setLeftAnchor(p, -20.);
		AnchorPane.setTopAnchor(p, 20.);
		ahh.getChildren().addAll(p);
	}*/
	
	/**Invariant: AnchorPane an has no children to start with*/
	/*private void hexWorldMap(Double size, AnchorPane an) {
		int [] a = w.worlddim();
		for (int place = 0; place < a[0]; place++) {
			for (int ptwo = 0; ptwo < a[1]; ptwo ++) {
				int col = ptwo;
				int row = place + (col + 1)/2;
				an.getChildren().add(new Hexagon(size, row, col, w, an));
			}
		}
		hexes = new Hexagon[an.getChildren().size()];
		Iterator<Node> it = an.getChildren().iterator();
		for (int x = 0; x < hexes.length; x ++) {
			hexes[x] = (Hexagon) it.next();
		}	
		resize(an);
		xjust = (xcoord - xspace()) / 2;
		yjust = (ycoord - yspace()) / 2;
		reframe(an);
	}*/
	
	
	/**Invariant: AnchorPane an has no children to start with*/
	private void hexWorldMap(Double size) {
		int [] a = w.worlddim();
		hexes = new Hexagon [a[0] * a[1]];
		int there = 0;
		for (int place = 0; place < a[0]; place++) {
			for (int ptwo = 0; ptwo < a[1]; ptwo ++) {
				int col = ptwo;
				int row = place + (col + 1)/2;
				hexes[there] = new Hexagon(size, row, col, w);
				there ++;
			}
		}
		h.setHexGrid(hexes);
		h.resize(w);
		//xjust = (xcoord - h.xspace(w)) / 2;
		//yjust = (ycoord - h.yspace(w)) / 2;
		//h.zoom(amount, w);
		//reframe(an);
	}
	
	/**Adjusts the size of everything in the anchorpane to accomodate the new
	 * size of the anchorpane
	 * @param anch
	 */
	private void resize(AnchorPane anch) {
		int [] a = w.worlddim();
		int place = 0;
		for (int row = 0; row < a[0]; row++) {
			for (int col = 0; col < a[1]; col ++) {
				double size = getsize();
				double [] posit = getplace(row,col,size);
				Hexagon temp = hexes[place];
				temp.position = posit;
				temp.resize(size);
				//temp.getInhabitant(w, anch); TODO put the line back
				place(temp, anch, posit);
				place ++;
			}
		}
	}

	/** returns the place of a hex given the size (side length) of the hexes,
	 * the row its in, and the column. Size calculation should depend on xcoord 
	 * @param row
	 * @param col
	 * @param size
	 * @return
	 */
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
	
	public void place(Shape p, AnchorPane a, double [] posit) {
		AnchorPane.setLeftAnchor(p, posit[0]);
		AnchorPane.setTopAnchor(p, posit[1]);
	}
	
	/**Calculates the size of the hexagons based on the size of the anchorpane*/
	private double getsize() {
		int [] a = w.worlddim();
		double ysize = (ycoord - 23) / (rtthr * (a[0] + .5)); //The 23 is pretty arbitrary here too
		double xsize = (xcoord / (a[1] * 1.5 + .5));
		return ysize < xsize ? ysize : xsize;
	}
	
	/**returns the total number of pixel things that the entire hexagonal pattern
	 * occupies.
	 * @return
	 */
	private double xspace() {
		int [] a = w.worlddim();
		double size = (getsize() * (1 + .5)) * a[1]; //getsize * 1.5 = xdist of a hex
		size += .5 * getsize();
		return size;
	}
	
	private double yspace() {
		int [] a = w.worlddim();
		double size = (getsize() * rtthr) * a[0] + 23; // getsize * rtthr * 2/2 is the yheight
		size += getsize() * rtthr / 2;
		System.out.println(size);
		return size;
	}
	
	/**Changes the scale by both adjusting the anchorpane size and re-justifying the
	 * anchorpane within the greater anchorpane.
	 * @param amount
	 * @param a
	 */
	private void scalechange (double amount, AnchorPane a) {
		this.xcoord += amount;
		this.ycoord += amount;
		xjust -= amount / 2;
		yjust -= amount / 2;
		reframe(a);
	}
	
	/**justifies the anchorpane within the greater anchorpane*/
	private void reframe (AnchorPane a) {
		AnchorPane.setLeftAnchor(a, xjust);
		AnchorPane.setTopAnchor(a, yjust);
	}
	
	private void updateInhabitants(AnchorPane a) {
		for (Hexagon them : hexes) {
			//them.getInhabitant(w, a); TODO, put the line back
		}
	} 

}
