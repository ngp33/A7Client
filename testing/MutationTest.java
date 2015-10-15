package testing;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Ignore;
import org.junit.Test;

import ast.Node;
import ast.ProgramImpl;
import exceptions.SyntaxError;
import mutation.*;
import parse.Parser;
import parse.ParserImpl;
import parse.Tokenizer;

public class MutationTest {

	
	@Test
	public void remove() throws SyntaxError {
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17; nearby[3] = 0 and ENERGY > 2500 --> bud; {ENERGY > SIZE * 400 and SIZE < 7} --> grow; ahead[0] < -1 and ENERGY < 500 * SIZE --> eat; (ahead[1] / 10 mod 100) != 17 and ahead[1] > 0 --> attack; ahead[1] < -5 --> forward; ahead[2] < -10 and ahead[1] = 0 --> forward; ahead[3] < -15 and ahead[1] = 0 --> forward; ahead[4] < -20 and ahead[1] = 0 --> forward; nearby[0] > 0 and nearby[3] = 0 --> backward; ahead[1] < -1 and { ENERGY > 2500 or SIZE > 7 } --> serve[ENERGY / 42]; random[3] = 1 --> left; 1 = 1 --> wait;");
		Tokenizer t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);
		
		Node nodeToMutate;
		
		MutRemove mr = new MutRemove();
		mr.initiate(r);
		
		/*nodeToMutate = r.nodeAt(0);
		mr.findparent(nodeToMutate, 0);
		System.out.println(mr.Mutate(r.nodeAt(0)));*/
	
		mr.findparent(r, 1);
		assertTrue(mr.parent == r);
		mr.Mutate(r.nodeAt(1));
		for (int place = 0; place < r.size(); place++){
			mr.findparent(r, place);
		}
		System.out.println(r.prettyPrint(new StringBuilder()));
		
		
		/*for (int them = 0; them < r.size(); them ++){
			System.out.println(r.nodeAt(them).prettyPrint(sb));
			sb = new StringBuilder();
		}*/
		//MutRemove mr = new MutRemove(r);
		//mr.Mutate(r.getRandomNode(Node.class));
	}
	
	@Test
	public void swap() throws SyntaxError {
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17; nearby[3] = 0 and ENERGY > 2500 --> bud; {ENERGY > SIZE * 400 and SIZE < 7} --> grow; ahead[0] < -1 and ENERGY < 500 * SIZE --> eat; (ahead[1] / 10 mod 100) != 17 and ahead[1] > 0 --> attack; ahead[1] < -5 --> forward; ahead[2] < -10 and ahead[1] = 0 --> forward; ahead[3] < -15 and ahead[1] = 0 --> forward; ahead[4] < -20 and ahead[1] = 0 --> forward; nearby[0] > 0 and nearby[3] = 0 --> backward; ahead[1] < -1 and { ENERGY > 2500 or SIZE > 7 } --> serve[ENERGY / 42]; random[3] = 1 --> left; 1 = 1 --> wait;");
		Tokenizer t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);
		StringBuilder sb = new StringBuilder();
		
		Node nodeToMutate;
		
		MutSwap ms = new MutSwap();
		ms.initiate(r);
		
		System.out.println(r.prettyPrint(sb));
		sb = new StringBuilder();
		nodeToMutate = r.nodeAt(0);
		System.out.println(ms.Mutate(r.nodeAt(0)));
		System.out.println(r.prettyPrint(sb));
		sb = new StringBuilder();

		
		nodeToMutate = r.nodeAt(1);
		System.out.println(ms.Mutate(r.nodeAt(1)));
		
		
	}
	
	@Ignore
	@Test
	public void replace(){
		
	}
	
	@Test
	public void findparent(){
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17;");
		Parser p = new ParserImpl();
		p.parse(s);
		assert
	}
	
}
