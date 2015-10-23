package testing;

import static org.junit.Assert.*;
import world.*;

import org.junit.Test;

public class JWorld {

	@Test
	public void worldConstants() {
		World w = new World(8, 6);
		
		assertEquals(w.BASE_DAMAGE, 100);
		assertEquals(w.COLUMNS, 50);
		assertEquals(w.MIN_MEMORY, 8);
	}
	
	@Test
	public void worldSetGet() {
		World w = new World(8, 6);
		
		w.setHex(0, 0, new Food(5));
		assertEquals(w.getHex(0, 0).getHexInfo(), "5 units of food.");
		
		w.setHex(7, 5, new Food(0));
		assertEquals(w.getHex(7, 5).getHexInfo(), "An empty hex.");
		
		assertEquals(w.getHex(7, 7).getHexInfo(), "A rock.");
	}

}
