package gui;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import world.Critter;

public class MemTableRow {
	private Critter critter;
	private final SimpleIntegerProperty index;
	
	public MemTableRow(int i, Critter c) {
		critter = c;
		index = new SimpleIntegerProperty(i);
	}
	
	public SimpleIntegerProperty indexProperty() {
		return index;
	}
	
	public SimpleIntegerProperty valueProperty() {
		if (critter == null) {
			return new SimpleIntegerProperty();
		}
		return new SimpleIntegerProperty(critter.mem[index.get()]);
	}
	
	public void setCritter(Critter c) {
		critter = c;
	}
	
}
