package parse;
import org.junit.*;

import exceptions.SyntaxError;
import parse.Tokenizer.TokenizerIOException;

import static org.junit.Assert.*;

import java.io.StringReader;

public class JToken {
	Tokenizer t;
	
	
	@Before
	public void init(){
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17;");
		t = new Tokenizer(s);
	}
	
	@Test
	public void eats(){
		try {
			ParserImpl.consume(t,TokenType.ABV_POSTURE);
		} catch (TokenizerIOException | SyntaxError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(t.peek().getType().equals(TokenType.NE));
	}
	
	@Test
	public void rulenum(){
		int k = ParserImpl.rulenum(t);
		assertTrue(k == 1);
	}

}
