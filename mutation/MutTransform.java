package mutation;

import ast.Node;

public class MutTransform extends MutationImpl {

	@Override
	public boolean equals(Mutation m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Mutate(Node n) {
		if (n instanceof Transformable) {
			((Transformable) n).transform();
			
			return true;
		}
		
		return false;
	}

}
