package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import world.World;

public class CritterGraphic extends Inhabitant {

	//ImageView iv;
	double scale;
	CircleImage ci;
	double rttwo = 1.414213562;
	double rtthr = 1.732050808;
	//double leftpadding; 
	//double rightpadding;//this is assigned based on the direction of the critter so that the critter is
	//centralized. The idea is that the critter represents a square image which will be circumscribed
	//by a circle. The shortest distance from the left most and top most points in the square to the
	//circle is determined by the padding. It might end up making sense to create a pseudo image which
	//exists in a circle as a class.
	
	
	public CritterGraphic (World w, int row, int col, double size, AnchorPane a, double [] p) {
		super(w, col, col, size, p);
		//iv = new ImageView(new Image("critter.png"));
		ci = new CircleImage(new Image("critter.png"));
	}

	public CritterGraphic(Hexagon h, World w, AnchorPane a) {
		super (h,w,a);
		//iv = new ImageView(new Image("critter.png"));
		//a.getChildren().add(iv);
		ci = new CircleImage(new Image("critter.png"));
		ci.add(a);
		sizeupdate(h.size, h.position);
		//Add something to make the anchorpane work (so that the graphics are a child of the anchorpane)
	}

	@Override
	public void sizeupdate(double size, double [] position) {
		scale = size;//some modifier for the size based on the critters size.
		//iv.setFitWidth(scale);
		//iv.setFitHeight(scale);
		//AnchorPane.setLeftAnchor(iv, position[0]);
		//AnchorPane.setTopAnchor(iv, position[1]);
		position[0] += size - scale / rttwo;
		position[1] += size * rtthr / 2 - scale / rttwo;
		ci.setSize(scale);
		ci.setAnchors(position);
		
		
	}

	
	

}
