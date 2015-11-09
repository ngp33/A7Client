package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**A square image inscribed in a circle */
public class CircleImage {
	double rttwo = 1.414213562;
	ImageView iv;
	double size;
	
	public CircleImage(Image i) {
		iv = new ImageView(i);
	}
	
	public void setSize(double size) {
		iv.setFitWidth(size);
		iv.setFitHeight(size);
		this.size = size;
	}
	
	/**Given the distance to the circle, this will return the coordinates to draw square within */
	private double [] position (double [] position) {
		return (new double [] {position[0] + xDist(), position[1] + yDist()});
	}
	
	public void rotate(double degrees) {
		iv.setRotate(degrees);
	}
	
	private double xDist() {
		double a = Math.abs(Math.sin(Math.toRadians(iv.getRotate() + 45)));
		double b = Math.abs(Math.sin(Math.toRadians(iv.getRotate() + 135)));
		a = a > b ? a : b;
		a = a * size / rttwo;
		return size / rttwo - a;
	}
	
	private double yDist() {
		double a = Math.abs(Math.sin(Math.toRadians(iv.getRotate() - 45)));
		double b = Math.abs(Math.sin(Math.toRadians(iv.getRotate() - 135)));
		a = a > b ? a : b;
		a = a * size / rttwo;
		return size / rttwo - a;
	}
	
	public void add (AnchorPane a) {
		a.getChildren().add(iv);
	}
	
	public void setAnchors(double [] oldposition) {
		oldposition = position(oldposition);
		AnchorPane.setLeftAnchor(iv, oldposition[0]);
		AnchorPane.setTopAnchor(iv, oldposition[1]);
	}
	

}
