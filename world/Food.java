package world;

public class Food extends Hex {
	
	int quantity;

	@Override
	int getNumRep() {
		return quantity;
	}
	
	void addFood(int qty) {
		quantity += qty;
	}

	@Override
	char getASCIIRep() {
		if (quantity == 0) {
			return '-';
		} else {
			return 'F';
		}
	}

	@Override
	String description() {
		// TODO Auto-generated method stub
		return null;
	}

}
