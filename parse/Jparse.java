package parse;
import org.junit.*;

import ast.Expr;
import ast.Rule;
import exceptions.SyntaxError;

import static org.junit.Assert.*;

import java.io.StringReader;

public class Jparse {
	StringReader s;
	Tokenizer t;
	StringBuilder sb = new StringBuilder();
	
	@Before
	public void init(){
		
	}
	
	@Test
	public void factornum() throws SyntaxError{
		s = new StringReader("4");
		t = new Tokenizer(s);
		Expr e = ParserImpl.parseFactor(t);
		e.prettyPrint(sb);
		String sr = sb.toString();
		assertTrue(sr.equals(("4 ")));
	}
	
	@Test
	public void factorneg() throws SyntaxError{
		//TODO figure out factorneg
	}
	
	@Test
	public void thra() throws SyntaxError{
		s = new StringReader("mem[7] != 17 --> mem[7] := 17;");
		t = new Tokenizer(s);

		Rule r = ParserImpl.parseRule(t);
		System.out.println(r.prettyPrint(sb));
		
	}
	@Test
	public void rule2() throws SyntaxError{
		s = new StringReader("nearby[3] = 0 and ENERGY > 2500 --> bud;");
		t = new Tokenizer(s);
		Rule r = ParserImpl.parseRule(t);
		System.out.println(r.prettyPrint(sb));
	}
	@Test
	public void ruletest() throws SyntaxError{
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
}
