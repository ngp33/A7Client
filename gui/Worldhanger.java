package gui;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import ast.ProgramImpl;
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
import parse.Parser;
import parse.ParserImpl;
import world.Critter;
import world.Food;
import world.Rock;
import world.World;

//Problem: figure out how to order the inhabitant maker so that it makes inhabitants and updates them
//After the hexes all have positions.

public class Worldhanger extends Application implements Observer {
	private World w = new World(9,6, "hi");
	private int xcoord = 400;//Only alter these (xcoord, ycoord) using scalechange
	private int ycoord = 400;
	private Hexagon [] hexes; //ultimately an array of hex graphic objects which is ordered
	//from 
	Double rtthr = 1.732050808;
	private Hexgrid h;
	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		w.replace(new Rock(), w.getHex(4, 2));
		w.replace(new Food(23), w.getHex(2, 1));
		int [] mem = new int [] {8,1,3,2,100,0,0,0};
		Random r = new Random();
		Parser p = new ParserImpl();
		ProgramImpl pr = (ProgramImpl) p.parse(new FileReader("example-rules.txt"));
		Critter c = new Critter(mem, r, pr, w);
		c.direction = 1;
		w.replace(c, w.getHex(4, 3));
		w.addCritter(c);
		Group g = new Group();
		Scene s = new Scene(g);
		primaryStage.setScene(s);
		primaryStage.setWidth(xcoord);
		primaryStage.setHeight(ycoord);
		h = new Hexgrid(g, xcoord, ycoord);
		hexWorldMap(h.getsize(w));
		h.objectUpdate(w);
		primaryStage.show();
		s.setOnMouseClicked(new EventHandler <Event>() {
			@Override
			public void handle(Event event) {
				w.advance();
				//System.out.println(w.getInfo());
				h.objectUpdate(w);
			}
		});
		
		s.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				handleKey(event.getCode());	
			}
		});
	}
	
	private void handleKey(KeyCode keyCode) {
		switch (keyCode.getName()) {
		case "A":
			h.shiftTransverse(3, 0);
			break;
		case "S":
			h.shiftTransverse(0, -3);
			break;
		case "D":
			h.shiftTransverse(-3, 0);
			break;
		case "W":
			h.shiftTransverse(0, 3);
			break;
		case "Up":
			h.zoom(3,w);
			break;
		case "Down":
			h.zoom(-3, w);
			break;
		default:
			break;
		}
	}
	
	
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
		h.center(w);
	}
	

	@Override
	public void update(Observable o, Object arg) {
		h.objectUpdate(w);
	} 

}
