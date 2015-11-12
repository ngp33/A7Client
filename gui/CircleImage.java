package gui;

import java.util.LinkedList;
import java.util.Observable;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**Really just an image that can be thought of as existing in the center of a circle within
 * which the square image resides*/
public class CircleImage extends Observable {
	double rttwo = 1.414213562;
	ImageView iv;
	double size;
	ColorAdjust ca;
	
	public CircleImage(Image i) {
		iv = new ImageView(i);
		ca = new ColorAdjust();
		
		iv.setOnMouseClicked(new EventHandler <Event>() {

			@Override
			public void handle(Event event) {
				setChanged();
				notifyObservers();
				
			}
			
		});

	}
	
	public void setSize(double size) {
		iv.setFitWidth(size);
		iv.setFitHeight(size);
		this.size = size;
	}
	
	/**Given the distance to the circle, this will return the coordinates to draw square within */
	private void position (double [] position) {
		position[0] += xDist();
		position[1] += yDist();
	}
	
	public void rotate(double degrees) {
		iv.setRotate(degrees);
	}
	
	private double xDist() {
		/*double a = Math.abs(Math.sin(Math.toRadians(iv.getRotate() + 45)));
		double b = Math.abs(Math.sin(Math.toRadians(iv.getRotate() + 135)));
		a = a > b ? a : b;
		a = a * size / rttwo;
		//return size / rttwo - a; */
		return size / rttwo - size / 2;
	}
	
	private double yDist() {
		/*double a = Math.abs(Math.sin(Math.toRadians(iv.getRotate() - 45)));
		double b = Math.abs(Math.sin(Math.toRadians(iv.getRotate() - 135)));
		a = a > b ? a : b;
		a = a * size / rttwo;
		//return size / rttwo - a; */
		return size / rttwo - size / 2;
	}
	
	public void add (AnchorPane a) {
		a.getChildren().add(iv);
	}
	
	public void setAnchors(double [] oldposition) {
		position(oldposition);
		AnchorPane.clearConstraints(iv);
		AnchorPane.setLeftAnchor(iv, oldposition[0]);
		AnchorPane.setTopAnchor(iv, oldposition[1]);
	}
	
	public void color(double proportionfull) {
		
		ca.setContrast(-1 + proportionfull * 2);
		iv.setEffect(ca);
	}
	
	public void remove(AnchorPane a) {
		a.getChildren().remove(iv);
		AnchorPane.clearConstraints(iv);
	}
	
	public double getDegree() {
		return iv.getRotate();
	}


	

}
