package mutation;

import ast.Node;
import ast.Program;

public class MutRemove extends ParentConsciousMutation {
	
	public MutRemove(Program tree) {
		type = MutationType.remove;
		AST = tree;
	}
	
	public MutRemove() {
		type = MutationType.remove;
	}

	@Override
	public boolean Mutate(Node n) {
		if (n instanceof Removable) {
			parent.replaceKid(n, ((Removable) n).getReplacement());
			return true;
		}
		
		return false;
	}
	
	/** takes a Node and mutates it according to rule 1 of mutations*/
	//I think the biggest deciding factor for how this mutation affects a node is
	//The node's primary subclass of Node (eg. expression, Condition etc) and whether the
	//node's children evaluate the same way it does.

}
