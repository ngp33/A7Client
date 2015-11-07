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
import javafx.stage.Stage;
import world.World;

public class Worldhanger extends Application {
	private World w = new World(7,5,"ease");
	private int xcoord = 400;//Only alter these (xcoord, ycoord) using scalechange
	private int ycoord = 400;
	private double xjust = 0.0;
	private double yjust = 0.0;
	private Hexagon [] hexes;
	Double rtthr = 1.732050808;
	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		Group g = new Group();
		AnchorPane b = new AnchorPane();
		AnchorPane a = new AnchorPane();
		g.getChildren().add(b);
		b.getChildren().add(a);
		AnchorPane.setLeftAnchor(a, xjust);
		AnchorPane.setTopAnchor(a, yjust);
		Scene s = new Scene(g);
		primaryStage.setScene(s);
		primaryStage.setWidth(xcoord);
		primaryStage.setHeight(ycoord);
		a.setPrefSize(xcoord, ycoord);
		//draw the world based on initial size conditions
		//drawhexes(a,0, new int [] {0});
		hexWorldMap(10.,a);
		primaryStage.show();
		a.setOnMouseClicked(new EventHandler <Event>() {

			@Override //Anything that resizes also has to adjust xjust, yjust
			public void handle(Event event) {
				//scalechange(false, -20, a);
				resize(a);
			}
			
		});
		
		s.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				handleKey(event.getCode(), a);
				
			}
			
			
		});

	}
	
	private void handleKey(KeyCode keyCode, AnchorPane a) {
		switch (keyCode.getName()) {
		case "A":
			xjust -= 3;
			reframe(a);
			break;
		case "S":
			yjust += 3;
			reframe(a);
			break;
		case "D":
			xjust += 3;
			reframe(a);
			break;
		case "W":
			yjust -= 3;
			reframe(a);
			break;
		case "Up":
			scalechange(3, a);
			resize(a);
			break;
		case "Down":
			scalechange(-3, a);
			resize(a);
			break;
		default:
			//System.out.println(keyCode.getName());
			break;
		}
	}
	
	public void drawhexes(AnchorPane ahh, int hexcircles, int[] center) {
		Polygon p = new Hexagon(20.0);
		AnchorPane.setLeftAnchor(p, -20.);
		AnchorPane.setTopAnchor(p, 20.);
		ahh.getChildren().addAll(p);
	}
	
	//private void drawhexes(AnchorPane ahh, int hexcircles, int [] center) {
		
	//}
	
	private void hexWorldMap(Double size, AnchorPane an) {
		int [] a = w.worlddim();
		for (int place = 0; place < a[0]; place++) {
			for (int ptwo = 0; ptwo < a[1]; ptwo ++) {
				an.getChildren().add(new Hexagon(size));
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
	}
	
	private void resize(AnchorPane anch) {
		int [] a = w.worlddim();
		int place = 0;
		for (int row = 0; row < a[0]; row++) {
			for (int col = 0; col < a[1]; col ++) {
				double size = getsize();
				double [] posit = getplace(row,col,size);
				Hexagon temp = hexes[place];
				temp.resize(size);
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
	
	private void place(Polygon p, AnchorPane a, double [] posit) {
		AnchorPane.setLeftAnchor(p, posit[0]);
		AnchorPane.setTopAnchor(p, posit[1]);
	}
	
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
		System.out.println(getsize());
		System.out.println(size);
		return size;
	}
	
	private double yspace() {
		int [] a = w.worlddim();
		double size = (getsize() * rtthr) * a[0] + 23; // getsize * rtthr * 2/2 is the yheight
		size += getsize() * rtthr / 2;
		System.out.println(size);
		return size;
	}
	
	private void scalechange (double amount, AnchorPane a) {
		/*if (xcoord) {
			this.xcoord += amount;
		}
		else {
			this.ycoord += amount;
		}*/
		this.xcoord += amount;
		this.ycoord += amount;
		xjust -= amount / 2;
		yjust -= amount / 2;
		reframe(a);
	}
	
	private void reframe (AnchorPane a) {
		AnchorPane.setLeftAnchor(a, xjust);
		AnchorPane.setTopAnchor(a, yjust);
	}

}
