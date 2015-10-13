package mutation;

import ast.Node;

public class MutSwap extends MutationImpl {

	@Override
	public boolean equals(Mutation m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	/** Biggest deal here is whether the node has 2+ children and whether they are of the same type*/
	public boolean Mutate(Node n) {
		if (n instanceof Swappable) {
			((Swappable) n).swapKids();
		}

		return false;
	}

}
