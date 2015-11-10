package gui;

import javafx.beans.property.SimpleStringProperty;

public class MemTableRow {
	private final SimpleStringProperty index;
	private final SimpleStringProperty value;
	
	public MemTableRow(String i, String v) {
		index = new SimpleStringProperty(i);
		value = new SimpleStringProperty(v);
	}
	
	public SimpleStringProperty indexProperty() {
		return index;
	}
	
	public SimpleStringProperty valueProperty() {
		return value;
	}
	
}
