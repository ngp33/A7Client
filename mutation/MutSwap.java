package mutation;

import ast.Node;

//May result in the same exact AST
public class MutSwap extends MutationImpl {

	@Override
	public boolean equals(Mutation m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Mutate(Node n) {
		if (n instanceof Swappable) {
			((Swappable) n).swapKids();
		}

		return false;
	}

}
