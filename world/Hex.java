package world;

public abstract class Hex {

	int row;
	int col;
	
	abstract int getNumRep();
	
	abstract char getASCIIRep();
	
	abstract String description();
}
