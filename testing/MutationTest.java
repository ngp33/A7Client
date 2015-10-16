package testing;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.Arrays;

import org.junit.Ignore;
import org.junit.Test;

import ast.Node;
import ast.Program;
import ast.ProgramImpl;
import exceptions.SyntaxError;
import mutation.*;
import ast.*;
import parse.Parser;
import parse.ParserImpl;
import parse.Tokenizer;

public class MutationTest {

	@Ignore
	@Test
	public void remove() throws SyntaxError {
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17; nearby[3] = 0 and ENERGY > 2500 --> bud; {ENERGY > SIZE * 400 and SIZE < 7} --> grow; ahead[0] < -1 and ENERGY < 500 * SIZE --> eat; (ahead[1] / 10 mod 100) != 17 and ahead[1] > 0 --> attack; ahead[1] < -5 --> forward; ahead[2] < -10 and ahead[1] = 0 --> forward; ahead[3] < -15 and ahead[1] = 0 --> forward; ahead[4] < -20 and ahead[1] = 0 --> forward; nearby[0] > 0 and nearby[3] = 0 --> backward; ahead[1] < -1 and { ENERGY > 2500 or SIZE > 7 } --> serve[ENERGY / 42]; random[3] = 1 --> left; 1 = 1 --> wait;");
		Tokenizer t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);
		StringBuilder sb = new StringBuilder();
		
		System.out.println(r.prettyPrint(sb));
		sb = new StringBuilder();
		
		MutRemove mr = new MutRemove();
		mr.initiate(r);
		
		for (int i = 1; i < r.size(); i++) {
			Node n = r.nodeAt(i);
			mr.findparent(r, i);
			
			if (mr.Mutate(n)) {
				System.out.println("MUTATION ON " + i + ":");
				System.out.println(r.prettyPrint(sb));
				sb = new StringBuilder();
			} else {
				System.out.println("MUTATION ON " + i + " NOT SUPPORTED");
			}
		}
		/*nodeToMutate = r.nodeAt(0);
		mr.findparent(nodeToMutate, 0);
		System.out.println(mr.Mutate(r.nodeAt(0)));*/
	
		mr.findparent(r, 1);
		assertTrue(mr.parent == r);
		mr.Mutate(r.nodeAt(1));
		int k = r.size();
		for (int place = 1; place < 136; place++){
			mr.findparent(r, place);
		}
		System.out.println(r.nodeAt(142).prettyPrint(new StringBuilder()));
		for (int place = 136; place < k; place ++){
			mr.findparent(r, place);
		}
	}
	
	@Ignore
	@Test
	public void swap() throws SyntaxError {
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17; nearby[3] = 0 and ENERGY > 2500 --> bud; {ENERGY > SIZE * 400 and SIZE < 7} --> grow; ahead[0] < -1 and ENERGY < 500 * SIZE --> eat; (ahead[1] / 10 mod 100) != 17 and ahead[1] > 0 --> attack; ahead[1] < -5 --> forward; ahead[2] < -10 and ahead[1] = 0 --> forward; ahead[3] < -15 and ahead[1] = 0 --> forward; ahead[4] < -20 and ahead[1] = 0 --> forward; nearby[0] > 0 and nearby[3] = 0 --> backward; ahead[1] < -1 and { ENERGY > 2500 or SIZE > 7 } --> serve[ENERGY / 42]; random[3] = 1 --> left; 1 = 1 --> wait;");
		Tokenizer t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);
		StringBuilder sb = new StringBuilder();
		
		System.out.println(r.prettyPrint(sb));
		sb = new StringBuilder();
		
		MutSwap ms = new MutSwap();
		ms.initiate(r);
		
		for (int i = 0; i < r.size(); i++) {
			Node n = r.nodeAt(i);
			
			if (ms.Mutate(n)) {
				System.out.println("MUTATION ON " + i + ":");
				System.out.println(r.prettyPrint(sb));
				sb = new StringBuilder();
			} else {
				System.out.println("MUTATION ON " + i + " NOT SUPPORTED");
			}
		}
		
	}
	
	@Ignore
	@Test
	public void replace() throws SyntaxError {
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17; nearby[3] = 0 and ENERGY > 2500 --> bud; {ENERGY > SIZE * 400 and SIZE < 7} --> grow; ahead[0] < -1 and ENERGY < 500 * SIZE --> eat; (ahead[1] / 10 mod 100) != 17 and ahead[1] > 0 --> attack; ahead[1] < -5 --> forward; ahead[2] < -10 and ahead[1] = 0 --> forward; ahead[3] < -15 and ahead[1] = 0 --> forward; ahead[4] < -20 and ahead[1] = 0 --> forward; nearby[0] > 0 and nearby[3] = 0 --> backward; ahead[1] < -1 and { ENERGY > 2500 or SIZE > 7 } --> serve[ENERGY / 42]; random[3] = 1 --> left; 1 = 1 --> wait;");
		Tokenizer t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);
		StringBuilder sb = new StringBuilder();
		
		System.out.println(r.prettyPrint(sb));
		sb = new StringBuilder();
		
		MutReplace mr = new MutReplace();
		mr.initiate(r);
		
		for (int i = 1; i < r.size(); i++) {
			Node n = r.nodeAt(i);
			mr.findparent(r, i);
			
			if (mr.Mutate(n)) {
				System.out.println("MUTATION ON " + i + ":");
				System.out.println(r.prettyPrint(sb));
				sb = new StringBuilder();
			} else {
				System.out.println("MUTATION ON " + i + " NOT SUPPORTED");
			}
		}
	}
	
	/*@Ignore
	@Test
	public void replace2() throws SyntaxError {
		StringReader s = new StringReader("mem[7] != 17 --> mem[7] := 17;");
		Tokenizer t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);
		StringBuilder sb = new StringBuilder();
		
		System.out.println(r.prettyPrint(sb));
		sb = new StringBuilder();
		
		MutReplace mr = new MutReplace();
		mr.initiate(r);
		
		for (int i = 1; i < r.size(); i++) {
			Node n = r.nodeAt(i);
			mr.loadAncestors(i);
			mr.findparent(r, i);
			
			if (mr.Mutate(n)) {
				System.out.println("MUTATION ON " + i + ":");
				System.out.println(r.prettyPrint(sb));
				sb = new StringBuilder();
			} else {
				System.out.println("MUTATION ON " + i + " NOT SUPPORTED");
			}
		}
	}*/
	
	/*@Ignore
	@Test
	public void getAncestorsTest() throws SyntaxError {
		StringReader s = new StringReader("mem[7] != 17 --> mem[7] := 17;");
		Tokenizer t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);
		
		MutReplace mr = new MutReplace();
		mr.initiate(r);
		
		System.out.println(r.nodeAt(8));
		
		mr.loadAncestors(8);
		
		mr.Mutate(r.nodeAt(8));
	}*/
	
	@Ignore
	@Test
	public void transform() throws SyntaxError {
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17; nearby[3] = 0 and ENERGY > 2500 --> bud; {ENERGY > SIZE * 400 and SIZE < 7} --> grow; ahead[0] < -1 and ENERGY < 500 * SIZE --> eat; (ahead[1] / 10 mod 100) != 17 and ahead[1] > 0 --> attack; ahead[1] < -5 --> forward; ahead[2] < -10 and ahead[1] = 0 --> forward; ahead[3] < -15 and ahead[1] = 0 --> forward; ahead[4] < -20 and ahead[1] = 0 --> forward; nearby[0] > 0 and nearby[3] = 0 --> backward; ahead[1] < -1 and { ENERGY > 2500 or SIZE > 7 } --> serve[ENERGY / 42]; random[3] = 1 --> left; 1 = 1 --> wait;");
		Tokenizer t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);
		StringBuilder sb = new StringBuilder();
		
		MutTransform mt = new MutTransform();
		mt.initiate(r);
		
		System.out.println(r.prettyPrint(sb));
		sb = new StringBuilder();
		
		for (int i = 0; i < r.size(); i++) {
			Node n = r.nodeAt(i);
			
			if (mt.Mutate(n)) {
				System.out.println("MUTATION ON " + i + ":");
				System.out.println(r.prettyPrint(sb));
				sb = new StringBuilder();
			} else {
				System.out.println("MUTATION ON " + i + " NOT SUPPORTED");
			}
		}
	}
	
	@Test
	public void insert() throws SyntaxError {
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17; nearby[3] = 0 and ENERGY > 2500 --> bud; {ENERGY > SIZE * 400 and SIZE < 7} --> grow; ahead[0] < -1 and ENERGY < 500 * SIZE --> eat; (ahead[1] / 10 mod 100) != 17 and ahead[1] > 0 --> attack; ahead[1] < -5 --> forward; ahead[2] < -10 and ahead[1] = 0 --> forward; ahead[3] < -15 and ahead[1] = 0 --> forward; ahead[4] < -20 and ahead[1] = 0 --> forward; nearby[0] > 0 and nearby[3] = 0 --> backward; ahead[1] < -1 and { ENERGY > 2500 or SIZE > 7 } --> serve[ENERGY / 42]; random[3] = 1 --> left; 1 = 1 --> wait;");
		Tokenizer t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);
		StringBuilder sb = new StringBuilder();
		
		MutInsert mi = new MutInsert();
		mi.initiate(r);
		
		System.out.println(r.prettyPrint(sb));
		sb = new StringBuilder();
		
		for (int i = 1; i < r.size(); i++) {
			Node n = r.nodeAt(i);
			mi.findparent(r, i);
			
			if (mi.Mutate(n)) {
				System.out.println("MUTATION ON " + i + ":");
				System.out.println(r.prettyPrint(sb));
				sb = new StringBuilder();
				
				s = new StringReader("POSTURE != 17 --> POSTURE := 17; nearby[3] = 0 and ENERGY > 2500 --> bud; {ENERGY > SIZE * 400 and SIZE < 7} --> grow; ahead[0] < -1 and ENERGY < 500 * SIZE --> eat; (ahead[1] / 10 mod 100) != 17 and ahead[1] > 0 --> attack; ahead[1] < -5 --> forward; ahead[2] < -10 and ahead[1] = 0 --> forward; ahead[3] < -15 and ahead[1] = 0 --> forward; ahead[4] < -20 and ahead[1] = 0 --> forward; nearby[0] > 0 and nearby[3] = 0 --> backward; ahead[1] < -1 and { ENERGY > 2500 or SIZE > 7 } --> serve[ENERGY / 42]; random[3] = 1 --> left; 1 = 1 --> wait;");
				t = new Tokenizer(s);
				r = ParserImpl.parseProgram(t);
			} else {
				System.out.println("MUTATION ON " + i + " NOT SUPPORTED");
			}
		}
	}
	
	@Ignore
	@Test
	public void duplicate() throws SyntaxError {
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17; nearby[3] = 0 and ENERGY > 2500 --> bud; {ENERGY > SIZE * 400 and SIZE < 7} --> grow; ahead[0] < -1 and ENERGY < 500 * SIZE --> eat; (ahead[1] / 10 mod 100) != 17 and ahead[1] > 0 --> attack; ahead[1] < -5 --> forward; ahead[2] < -10 and ahead[1] = 0 --> forward; ahead[3] < -15 and ahead[1] = 0 --> forward; ahead[4] < -20 and ahead[1] = 0 --> forward; nearby[0] > 0 and nearby[3] = 0 --> backward; ahead[1] < -1 and { ENERGY > 2500 or SIZE > 7 } --> serve[ENERGY / 42]; random[3] = 1 --> left; 1 = 1 --> wait;");
		Tokenizer t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);
		StringBuilder sb = new StringBuilder();
		
		MutDuplicate md = new MutDuplicate();
		md.initiate(r);
		
		System.out.println(r.prettyPrint(sb));
		sb = new StringBuilder();
		
		for (int i = 0; i < r.size(); i++) {
			Node n = r.nodeAt(i);
			
			if (md.Mutate(n)) {
				System.out.println("MUTATION ON " + i + ":");
				System.out.println(r.prettyPrint(sb));
				sb = new StringBuilder();
			} else {
				System.out.println("MUTATION ON " + i + " NOT SUPPORTED");
			}
		}
	}
	
	@Ignore
	@Test
	public void findparent(){
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17;");
		Parser p = new ParserImpl();

		Program q = p.parse(s);
		MutRemove m = new MutRemove();
		m.initiate(q);
		m.findparent(q, 1);
		assertTrue(m.parent == q);
		m.findparent(q, 2);
		assertTrue(m.parent == q.nodeAt(1));
		m.findparent(q, 3);
		assertTrue(m.parent == q.nodeAt(2));
		m.findparent(q, 4);
		assertTrue(m.parent == q.nodeAt(3));
		m.findparent(q, 5);
		assertTrue(m.parent == q.nodeAt(2));
	}
	
	/*@Test
	public void checkancestor(){
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17; nearby[3] = 0 and ENERGY > 2500 --> bud;");
		Parser p = new ParserImpl();

		Program q = p.parse(s);
		MutReplace m = new MutReplace();
		m.initiate(q);
		System.out.println(q.nodeAt(13).prettyPrint(new StringBuilder()));
		Node [] n = m.getAncestors(13);
	}*/
}
