package testing;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Random;

import org.junit.*;
import static org.junit.Assert.*;

import ast.ProgramImpl;
import parse.ParserImpl;
import world.Critter;
import world.World;

public class Timesteptest {
	World w;
	ProgramImpl p;
	ParserImpl parsing;
	StringReader str;
	Critter c;

	@Before
	public void init() throws FileNotFoundException {
		parsing = new ParserImpl();
		p = (ProgramImpl) parsing.parse(new FileReader("example-rules.txt"));
		w = new World(7, 5,"hi");
		w.emptyworld();
		c = new Critter(new int[] { 8, 2, 2, 2, 200, 1, 0, 10 }, new Random(), p, w);
		w.replace(c, w.getHex(2, 2));
		c.direction = 0;
		c.row = 2;
		c.col = 2;
	}
	
	@Test
	public void timestep() {
		
		c.timestep();
		p = (ProgramImpl) parsing.parse(new StringReader("( mem[ 6 ] mod 1000 ) < 6 --> mem[ 5 ] := 1 \n eat ;"));
		assertTrue(c.mostrecentrule.toString().equals(p.children[0].toString()));
	}
	
	@Test
	public void crittercopy() {
		Critter k = c.copy();
		assertTrue(k.genes.toString().equals(c.genes.toString()));
		assertTrue(Arrays.equals(k.mem, c.mem));
		assertTrue(k.w.equals(c.w));
		
	}

}
