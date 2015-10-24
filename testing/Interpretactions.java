package testing;
import org.junit.*;

import ast.ProgramImpl;
import parse.ParserImpl;
import world.Critter;
import world.World;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.Random;

public class Interpretactions {
	World w;
	ProgramImpl p;
	ParserImpl parsing;
	StringReader str;
	Critter c;
	
	@Before
	public void init() {
		parsing = new ParserImpl();
		p = (ProgramImpl) parsing.parse(new StringReader ("1 = 1 --> wait;"));
		w = new World (7,5);
		w.emptyworld();
		c = new Critter(new int [] {8,2,2,2,200,1,0,10}, new Random(), p, w);
		w.replace(c, w.getHex(2, 2));
		c.direction = 0;
		c.row = 2;
		c.col = 2;
		//Max energy: 1000
		//current energy: 200
	}
	
	@Test
	public void waited() {
		c.waiting();
		assertTrue(c.mem[4] == 202); //Tests that wait adds the proper amount of energy
		c.mem[4] = 1000;
		c.waiting();
		assertTrue(c.mem[4] == 1000); //Tests that energy doesn't exceed max
	}
	
	@Test
	public void turn(){
		c.turn(false);
		assertTrue(c.direction == 1);
		assertTrue(c.mem[4] == 198); //energy test
		c.turn(true);
		assertTrue(c.direction == 0);
		c.turn(true);
		assertTrue(c.direction == 5);
		c.turn(false);
		assertTrue(c.direction == 0);
	}
	
	@Test
	//TODO Make sure this test ensures that the world wasn't 'modified' (ie, trashed).
	
	public void move() {
		c.movement(true);
		assertTrue(c.row == 3);
		assertTrue(c.col == 2);
		assertTrue(c.mem[4] == 194); //energy test
		//assertTrue(c.w.getHex(2, 2) instanceof )
		c.movement(false);
		assertTrue(c.row == 2);
		assertTrue(c.col == 2);
		c.direction = 1;
		c.movement(true);
		assertTrue(c.col == 3);
		assertTrue(c.row == 3);
		c.movement(false);
		assertTrue(c.row == 2);
		assertTrue(c.col == 2);
		c.direction = 4;
		c.movement(true);
		assertTrue(c.row == 1);
		assertTrue(c.col == 1);
	}
	
	@Test
	public void eat(){
		w.putFood(200, new int [] {3,2});
		c.consume();
		assertTrue(c.mem[4] == 398);
		assertTrue(c.w.getNumRep(new int [] {3,2}) == 0);
		w.putFood(1000, new int [] {3,2});
		c.consume();
		assertTrue(c.mem[4] == 1000);
		assertTrue(w.getNumRep(new int [] {3,2} ) == -397);
	}
	
	@Test
	public void serve() {
		c.serve(100);
		assertTrue(c.mem[4] == 98);
		c.turn(false);
		assertTrue(c.mem[4] == 96); //mostly just a reminder to the viewer that turning costs energy
		c.serve(200);
		assertTrue(w.getNumRep(new int [] {3,3}) == -95);
		assertTrue(w.getNumRep(new int [] {2,2}) == - 401);
	}
	
	@Test
	public void shortserve() {
		c.serve(100);
		c.serve(40);
		assertTrue(w.getNumRep(new int [] {3,2}) == -141);
	}
	
	@Test
	public void bud() {
		c.mem = new int [] {9,2,2,2,1000,1,0,10, 9};
		c.bud();
		assertTrue(c.mem[4] == 82); // mostly testing the complexity rule calculator.
		assertTrue(w.getNumRep(new int [] {1, 2}) > 0);
		StringBuilder sb = new StringBuilder();
		Critter child = ((Critter) w.getHex(1, 2));
		child.genes.prettyPrint(sb);
		
		assertTrue(child.mem[0] == c.mem[0]);
		assertTrue(child.mem[1] == c.mem[1]);
		assertTrue(child.mem[2] == c.mem[2]);
		assertTrue(child.mem[3] == 1);
		assertTrue(child.mem[4] == 250);
		assertTrue(child.mem[5] == 1);
		assertTrue(child.mem[6] == 0);
		assertTrue(child.mem[7] == 0);
		assertTrue(child.mem[8] == 0);
		
		c.mem = new int [] {9,2,2,2,1000,1,0,10, 9}; //To test budding out of bounds.
		c.w.swap(c, c.w.getHex(1,2));
		c.bud();
		assertTrue(c.mem[4] == 82);
		
		c.w.swap(c,c.w.getHex(2,2));
		c.bud();
		assertTrue(c.w.getNumRep(new int [] {2,2}) < 0);
		assertTrue(c.w.getNumRep(new int [] {1,2}) > 0); //this is the critter which was budded earlier
	}
	
	@Test
	public void attack() {
		c.mem[4] = 1000;
		c.bud();
		Critter b = (Critter) w.getHex(1,2);
		c.mem[4] = 1000;
		c.direction = 3;
		c.attack();
		assertTrue(c.mem[4] == 990);
		assertTrue(b.mem[4] == 130 ); //I did the calculation for attack damage. Hopefully this is right.
		c.direction = 0;
		c.attack();
		assertTrue(c.mem[4] == 980);
		assertTrue(b.mem[4] == 130);
	}
	
	

}
