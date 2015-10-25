package testing;
import org.junit.*;

import ast.ProgramImpl;
import parse.ParserImpl;
import world.Critter;
import world.Crittermethods;
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
		w = new World (7,5, "yre");
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
	//TODO test tag and mate. Shouldn't be so bad since a fair amount of mate's code overlaps with bud.
	
	@Test
	public void mate() { //TODO more tests here (eg failed mates (from lack of
		//space and lack of energy), energy consumption in these cases)
		ProgramImpl j = (ProgramImpl) parsing.parse(new StringReader ("nearby[3] = 0 and ENERGY > 2500 --> bud; \n random[3] = 1 --> left;"));
		Critter g = new Critter(new int [] {9,1,5,2,1000,1,0,10,109}, c.r, j, w);
		c.mem[4] = 1000;
		g.w.replace(g, w.getHex(3, 2));
		g.direction = 3;
		g.mate();
		c.mate();
		assertTrue(c.mem[4] == 1000 - 5 * Crittermethods.complexitycalc(c));
		assertTrue(g.mem[4] == 1000 - 5 * Crittermethods.complexitycalc(g));
		Critter child = (w.getNumRep(new int [] {1,2}) > 0 ? (Critter) w.getHex(1,2) : (Critter) w.getHex(4, 2));
		
		assertTrue(child.mem[3] == 1);
		assertTrue(child.mem[4] == 250);
		assertTrue(child.mem[5] == 1);
		assertTrue(child.mem[6] == 0);
		assertTrue(child.mem[7] == 0);
		if (child.mem[0] == 9) {
			assertTrue(child.mem[8] == 0);
		}
		
		
		Critter e = new Critter(new int [] {9,1,5,2,1000,1,0,10,109}, c.r, j, w); //Tests energy works when baby cant
		Critter f = new Critter(new int [] {9,1,5,2,1000,1,0,10,109}, c.r, j, w);//be placed
		w.replace(e, w.getHex(2, 4)); //These were chosen because neither space has a hex behind it
		w.replace(f, w.getHex(3, 5));
		e.direction = 1;
		f.direction = 4;
		e.mate();
		f.mate();
		assertTrue(e.mem[4] == 998);
		assertTrue(f.mem[4] == 998);
		
		w.swap(e, w.getHex(3, 2)); //Tests energy works when a critter doesn't have sufficient energy to mate
		w.swap(f, w.getHex(4, 2));
		e.direction = 0;
		f.direction = 3;
		e.mem[4] = 10;
		e.mate();
		f.mate();
		assertTrue(e.mem[4] == 8);
		assertTrue(f.mem[4] == 996);
	}
	
	@Test
	public void tag() {
		Critter d = new Critter(new int [] {9,1,5,2,1000,1,0,10,109}, c.r, p, w);
		w.replace(d, w.getHex(3, 2));
		c.youreit(500); //Testing out of bounds tags
		assertTrue(d.mem[6] == 0);
		assertTrue(c.mem[4] == 198);
		c.youreit(-9);
		assertTrue(d.mem[6] == 0);
		c.youreit(10);
		assertTrue(d.mem[6] == 10);
	}
	
	@Test
	public void nearbyahead() {
		Critter d = new Critter(new int [] {9,1,5,2,1000,1,5,11,109}, c.r, p, w);
		d.direction = 1;
		w.replace(d, w.getHex(3, 2));
		Critter e = new Critter(new int [] {9,1,5,2,1000,1,0,10,109}, c.r, p, w);
		w.replace(e, w.getHex(4, 4));
		e.direction = 4; 
		int a = c.nearby(6);
		assertTrue( a == 205111);
		w.swap(d, w.getHex(3, 3));
		a = c.nearby(-1); //Tests the negative nearbys
		assertTrue(a == 205110);
		
		c.direction = 1;
		a = c.ahead(0);
		assertTrue(a == 200100);
		a = c.ahead(-5);
		assertTrue(a == 200100);
		a = c.ahead(2);
		assertTrue(a == 200103);
		
		c.direction = 2;
		a = c.ahead(1);
		assertTrue(a == 0);
		a = c.ahead(900);
		assertTrue(a == w.ROCK_VALUE);
	}
	
	@Test
	public void random () {
		assertTrue(c.random(-1) == 0);
	}

}
