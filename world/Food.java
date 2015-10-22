package world;

public class Food extends Hex {
	
	int quantity;

	@Override
	int getNumRep() {
		return -quantity-1;
	}
	
	void addFood(int qty) {
		quantity -= qty;
	}

}
