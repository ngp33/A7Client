package gui;

import java.io.FileReader;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import ast.ProgramImpl;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import parse.Parser;
import parse.ParserImpl;
import world.Critter;
import world.Food;
import world.Rock;
import world.World;


public class Worldhanger extends Application implements Observer {
	private World w = new World(9,6, "hi");
	//private World w = new World();
	private int xcoord = 400;//Only alter these (xcoord, ycoord) using scalechange
	private int ycoord = 400;
	private Hexagon [] hexes; //ultimately an array of hex graphic objects which is ordered
	private Hexgrid h;
	private double time;
	public static void main(String[] args) {
		launch(args);
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		w.replace(new Rock(), w.getHex(4, 2));
		w.replace(new Food(23), w.getHex(2, 1));
		w.addObserver(this);
		int [] mem = new int [] {8,1,1,2,1000,0,0,0};
		Random r = new Random();
		Parser p = new ParserImpl();
		ProgramImpl pr = (ProgramImpl) p.parse(new FileReader("example-rules.txt"));
		Critter c = new Critter(mem, r, pr, w);
		c.direction = 0;
		w.replace(c, w.getHex(4, 3));
		w.addCritter(c);
		HBox hb = new HBox();
		Group g = new Group();
		//Scene s = new Scene(g);
		Scene s = new Scene(hb);
		//hb.getChildren().add(g);
		primaryStage.setScene(s);
		primaryStage.setWidth(xcoord);
		primaryStage.setHeight(ycoord);
		ScrollPane str = new ScrollPane();
		h = new Hexgrid(str, g, xcoord, ycoord);
		hb.getChildren().add(str);
		hexWorldMap(h.getsize(w));
		h.objectUpdate(w);
		primaryStage.show();
		s.setOnMouseClicked(new EventHandler <Event>() {
			@Override
			public void handle(Event event) {
				w.advance();
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
			h.shiftTransverse(-h.xcoord/10, 0);
			//h.sp.setHvalue(h.sp.getHvalue() - .1);
			break;
		case "S":
			h.shiftTransverse(0, h.ycoord/10);
			//h.sp.setVvalue(h.sp.getVvalue() + .1);
			break;
		case "D":
			h.shiftTransverse(h.xcoord/10, 0);
			//h.sp.setHvalue(h.sp.getHvalue() + .1);
			break;
		case "W":
			h.shiftTransverse(0, -h.ycoord/10);
			//h.sp.setVvalue(h.sp.getVvalue() - .1);
			break;
		case "Up":
			h.zoom(3,w);
			break;
		case "Down":
			h.zoom(-3, w);
			break;
		case "Enter":
			while (h.xcoord > h.getsize(w) * 10) {
				h.zoom(10, w);
			}
		default:
			System.out.println(keyCode.getName());
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
		h.center(w);
	}
	

	@Override
	public void update(Observable o, Object arg) {
		if (System.nanoTime() - time > 100000000/3) {
			h.objectUpdate(w);
		}
		time = System.nanoTime();
	}

}
