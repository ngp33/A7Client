package testing;
import org.junit.*;

import ast.Expr;
import ast.Node;
import ast.ProgramImpl;
import ast.Rule;
import ast.Rulesll;
import exceptions.SyntaxError;
import parse.Parser;
import parse.ParserImpl;
import parse.Tokenizer;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringReader;

public class Jparse {
	StringReader s;
	Tokenizer t;
	StringBuilder sb = new StringBuilder();
	
	
	@Ignore
	@Test
	public void factornum() throws SyntaxError{
		s = new StringReader("4");
		t = new Tokenizer(s);
		Expr e = ParserImpl.parseFactor(t);
		e.prettyPrint(sb);
		String sr = sb.toString();
		assertTrue(sr.equals(("4 ")));
	}
	
	@Ignore
	@Test
	public void factorneg() throws SyntaxError{
		//TODO figure out factorneg
	}
	
	@Ignore
	@Test
	public void thra() throws SyntaxError{
		s = new StringReader("mem[7] != 17 --> mem[7] := 17;");
		t = new Tokenizer(s);

		Rule r = ParserImpl.parseRule(t);
		System.out.println(r.prettyPrint(sb));
		
	}
	
	@Ignore
	@Test
	public void rule2() throws SyntaxError{
		s = new StringReader("nearby[3] = 0 and ENERGY > 2500 --> bud; //testing12 323 \n 1 = 1 --> wait;");
		t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);
		System.out.println(r.prettyPrint(sb));
	}
	
	//@Ignore
	@Test
	public void command() throws SyntaxError{
		s = new StringReader("nearby[3] = 0 and ENERGY > 2500 --> mem[7] := 17\nbud;");
		t = new Tokenizer(s);
		Rule r = ParserImpl.parseRule(t);
		System.out.println(r.prettyPrint(sb));
	}
	
	@Test
	public void command1() throws SyntaxError{
		s = new StringReader("{ ahead[1] < -1 and ENERGY > 2500 } or SIZE > 7 --> serve[4 + ENERGY / 42]; //testhello ");
		t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);
		System.out.println(r.prettyPrint(sb));
	}
	
	@Ignore
	@Test
	public void ruletest() throws SyntaxError{ //TODO make sure it handles negatives and braces.
		String [] str = {"{ENERGY > SIZE * 400 and SIZE < 7} --> grow;",
				"ahead[0] < -1 and ENERGY < 500 * SIZE --> eat;",
				"(ahead[1] / 10 mod 100) != 17 and ahead[1] > 0 --> attack;",
				"ahead[1] < -5 --> forward;",
				"ahead[2] < -10 and ahead[1] = 0 --> forward;",
				"ahead[3] < -15 and ahead[1] = 0 --> forward;",
				"ahead[4] < -20 and ahead[1] = 0 --> forward;",
				"nearby[0] > 0 and nearby[3] = 0 --> backward;",
				"ahead[1] < -1 and { ENERGY > 2500 or SIZE > 7 } --> serve[ENERGY / 42];",
				"random[3] = 1 --> left;",
				"1 = 1 --> wait;"
		};
		for (String them : str){
			s = new StringReader(them);
			t = new Tokenizer(s);
			Rule r = ParserImpl.parseRule(t);
			System.out.println(r.prettyPrint(sb));
			sb = new StringBuilder();
		}
	}
	@Ignore
	@Test //the purpose of this is to test the nodeAt method. It at least sometimes works. Note that a
	//program and semicolon both constitute a node. The nodeAt may be buggy.
	public void nodeat() throws SyntaxError{
		s = new StringReader("nearby[0] > 0 and nearby[3] = 0 --> backward;");
		t = new Tokenizer(s);
		Rule r = ParserImpl.parseRule(t);
		Rulesll a = new Rulesll();
		a.add(r);
		ProgramImpl b = new ProgramImpl(a);
		int c = b.size();
		System.out.println(b.nodeAt(1).size());
		System.out.println(b.nodeAt(2).size());
		for (int large = 0; large < b.size(); large ++){
			Node temp = b.nodeAt(large);
			System.out.println(temp.prettyPrint(sb));
			sb = new StringBuilder();
		}
	}
	
	@Ignore
	@Test
	public void test() throws SyntaxError {
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17; nearby[3] = 0 and ENERGY > 2500 --> bud; {ENERGY > SIZE * 400 and SIZE < 7} --> grow; ahead[0] < -1 and ENERGY < 500 * SIZE --> eat; (ahead[1] / 10 mod 100) != 17 and ahead[1] > 0 --> attack; ahead[1] < -5 --> forward; ahead[2] < -10 and ahead[1] = 0 --> forward; ahead[3] < -15 and ahead[1] = 0 --> forward; ahead[4] < -20 and ahead[1] = 0 --> forward; nearby[0] > 0 and nearby[3] = 0 --> backward; ahead[1] < -1 and { ENERGY > 2500 or SIZE > 7 } --> serve[ENERGY / 42]; random[3] = 1 --> left; 1 = 1 --> wait;");
		t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);
		for (int them = 0; them < r.size(); them ++){
			System.out.println(r.nodeAt(them).prettyPrint(sb));
			sb = new StringBuilder();
		}
	}
	@Test
	public void testtwo() throws FileNotFoundException {
		Parser p = new ParserImpl();
		FileReader f = new FileReader("log.txt");
		ProgramImpl g = (ProgramImpl) p.parse(new FileReader("example_critter2.txt"));
		System.out.println(g.toString());
	}
	@Test
	public void testthree() throws FileNotFoundException {
		Parser p = new ParserImpl();
		FileReader f = new FileReader("a5wp.txt");
		ProgramImpl g = (ProgramImpl) p.parse(f);
		System.out.println(g.toString());
	}
}
