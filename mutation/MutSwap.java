package mutation;

import ast.Node;
import ast.Program;

public class MutSwap extends MutationImpl {
	
	public MutSwap(Program tree) {
		type = MutationType.swap;
		AST = tree;
	}
	
	public MutSwap() {
		type = MutationType.swap;
	}

	@Override
	public boolean Mutate(Node n) {
		if (n instanceof Swappable) {
			((Swappable) n).swapKids();
			return true;
		}

		return false;
	}

}
