package mutation;

import ast.Node;
import ast.Program;

public class MutTransform extends MutationImpl {
	
	public void initiate(Program tree) {
		type = MutationType.transform;
		AST = tree;
	}
	
	public MutTransform() {
		type = MutationType.transform;
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
