package mutation;

import ast.Node;

public class MutReplace extends ParentConsciousMutation {

	@Override
	public boolean equals(Mutation m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Mutate(Node n) {
		if (n instanceof Replacable) {
			Node replacement = ((Replacable) n).getRandomReplacement(AST);
			
			if (replacement == null) {
				return false;
			}
			
			parent.replaceKid(n, replacement);
		}
		
		return false;
	}
	
}
