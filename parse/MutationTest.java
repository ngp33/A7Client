package parse;

import static org.junit.Assert.*;

import java.io.StringReader;

import org.junit.Test;

import ast.Node;
import ast.ProgramImpl;
import exceptions.SyntaxError;
import mutation.MutRemove;

public class MutationTest {
	StringReader s;
	Tokenizer t;
	StringBuilder sb = new StringBuilder();

	@Test
	public void test() throws SyntaxError {
		StringReader s = new StringReader("POSTURE != 17 --> POSTURE := 17; nearby[3] = 0 and ENERGY > 2500 --> bud; {ENERGY > SIZE * 400 and SIZE < 7} --> grow; ahead[0] < -1 and ENERGY < 500 * SIZE --> eat; (ahead[1] / 10 mod 100) != 17 and ahead[1] > 0 --> attack; ahead[1] < -5 --> forward; ahead[2] < -10 and ahead[1] = 0 --> forward; ahead[3] < -15 and ahead[1] = 0 --> forward; ahead[4] < -20 and ahead[1] = 0 --> forward; nearby[0] > 0 and nearby[3] = 0 --> backward; ahead[1] < -1 and { ENERGY > 2500 or SIZE > 7 } --> serve[ENERGY / 42]; random[3] = 1 --> left; 1 = 1 --> wait;");
		t = new Tokenizer(s);
		ProgramImpl r = ParserImpl.parseProgram(t);

	}
	
}
