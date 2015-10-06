package parse;
import org.junit.*;

import ast.Expr;
import ast.Num;
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
		assertTrue(sr.equals(("4")));
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
}
