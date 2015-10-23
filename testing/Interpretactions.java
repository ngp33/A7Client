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
		w = new World (5,5);
		c = new Critter(new int [] {8,4,3,2,200,1,0,10}, new Random(), p, w);
		c.direction = 0;
		c.row = 2;
		c.col = 2;
		//Max energy: 500
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
		w.putFood(200, new int [] {2,3});
		c.consume();
		assertTrue(c.mem[4] == 398);
		w.putFood(1000, new int [] {2,3});
		c.consume();
		assertTrue(c.mem[4] == 998);
		assertTrue(w.getNumRep(new int [] {2,3} ) == -399);
	}
	
	@Test
	public void serve() {
		c.serve(100);
		assertTrue(c.mem[4] == 98);
		c.turn(false);
		c.serve(200);
		assertTrue(w.getNumRep(new int [] {3,3}) == -99);
		assertTrue(w.getNumRep(new int [] {2,2}) == - 400);
	}
	
	public void bud() {
		c.bud();
		assertTrue(w.getNumRep(new int [] {1, 2}) > 0);
	}
	
	public void attack() {
		
	}
	
	

}