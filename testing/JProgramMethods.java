package testing;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

import ast.Node;
import ast.ProgramImpl;
import ast.Rule;
import exceptions.SyntaxError;
import parse.ParserImpl;
import parse.Tokenizer;

public class JProgramMethods {

	@Test
	public void getRandomOrder() throws SyntaxError {
		StringReader s = new StringReader("mem[7] != 17 --> mem[7] := 17;");
		Tokenizer t = new Tokenizer(s);

		ProgramImpl r = ParserImpl.parseProgram(t);
		
		System.out.println(Arrays.toString(r.getRandomSearchOrder()));
	}
	
	//@Ignore
	@Test
	public void test() throws SyntaxError {
		StringReader s = new StringReader("mem[7] != 17 --> mem[7] := 17;");
		Tokenizer t = new Tokenizer(s);

		ProgramImpl r = ParserImpl.parseProgram(t);
		
		for (int i = 0; i < r.size(); i++) {
			System.out.println(r.nodeAt(i));
		}
	}
	
	@Ignore
	@Test
	public void randomNodeWithIgnore() throws SyntaxError {
		StringReader s = new StringReader("mem[7] != 17 --> mem[7] := 17;");
		Tokenizer t = new Tokenizer(s);

		ProgramImpl r = ParserImpl.parseProgram(t);
				
		Node[] used = new Node[r.size()];
		int i = 0;
		
		while (i < r.size()) {
			Node n = r.getRandomNode(Node.class, used);
			System.out.println(n);
			used[i] = n;
			i++;
		}
	}

}
