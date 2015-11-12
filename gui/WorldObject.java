package gui;

import java.util.Observable;
import java.util.Observer;


import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import world.World;

public class WorldObject implements Observer {
	private World w = new World(9, 6, "hi");
	// private World w = new World();
	private int xcoord = 400;// Only alter these (xcoord, ycoord) using scalechange
	private int ycoord = 400;
	private Hexagon[] hexes; // ultimately an array of hex graphic objects which
								// is ordered
	private Hexgrid h;
	private double time;

	public WorldObject(ScrollPane p, World w) {
		w.addObserver(this);
		Group g = new Group();
		h = new Hexgrid(p, g, xcoord, ycoord, w);
		//p.getChildren().add(h.sp);
		this.w = w;
		hexWorldMap(h.getsize());
		h.objectUpdate();
	}

	/** Invariant: AnchorPane an has no children to start with */
	private void hexWorldMap(Double size) {
		int[] a = w.worlddim();
		hexes = new Hexagon[a[0] * a[1]];
		int there = 0;
		for (int place = 0; place < a[0]; place++) {
			for (int ptwo = 0; ptwo < a[1]; ptwo++) {
				int col = ptwo;
				int row = place + (col + 1) / 2;
				hexes[there] = new Hexagon(size, row, col, w);
				there++;
			}
		}
		h.setHexGrid(hexes);
		h.center(w);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (System.nanoTime() - time > 100000000 / 3) {
			h.objectUpdate();
		}
		time = System.nanoTime();
	}
	
	
	public void zoom(int amount) {
		h.zoom(amount, w);
	}
	public void move (double dx, double dy) {
		h.shiftTransverse(dx * h.xcoord, dy * h.ycoord);
	}
	

}
