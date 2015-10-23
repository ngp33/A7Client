package world;

public class Food extends Hex {
	
	int quantity;
	
	
	public Food(int init) {
		quantity = init;
	}
	
	public Food() {
		quantity = 0;
	}
	

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
	public String description() {
		if (quantity == 0) {
			return "An empty hex.";
		} else {
			return quantity + " units of food.";
		}
	}

}
