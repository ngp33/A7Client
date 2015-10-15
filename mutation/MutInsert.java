package mutation;

import ast.Node;
import ast.Program;

public class MutInsert extends ParentConsciousMutation {
	
	public MutInsert(Program tree) {
		type = MutationType.insert;
		AST = tree;
	}
	
	public MutInsert() {
		type = MutationType.insert;
	}

	@Override
	public boolean Mutate(Node n) {
		if (n instanceof Reparentable) {
			Insertable toInsert = ((Reparentable) n).getNewParent();
			if (toInsert.fillInMissingKids(AST)) {
				parent.replaceKid(n, (Node) toInsert);
			
				return true;
			}
			
			return false;
		}
		
		return false;
	}

}
