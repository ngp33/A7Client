package mutation;

import ast.Node;
import ast.Program;

public class MutReplace extends ParentConsciousMutation {
	
	public void initiate(Program tree) {
		type = MutationType.replace;
		AST = tree;
	}
	
	public MutReplace() {
		type = MutationType.replace;
	}

	@Override
	public boolean Mutate(Node n) {
		if (n instanceof Replacable) {
			Node replacement = ((Replacable) n).getRandomReplacement(AST, new Node[] {});
			
			if (replacement == null) {
				return false;
			}
			
			parent.replaceKid(n, replacement);
		}
		
		return false;
	}

	@Override
	public String type() {
		return "The node and its children were replaced by another node in the AST";
	}
	
}
