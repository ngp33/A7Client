package mutation;

import ast.Node;

public class MutInsert extends ParentConsciousMutation {

	@Override
	public boolean equals(Mutation m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Mutate(Node n) {
		if (n instanceof Reparentable) {
			Insertable toInsert = ((Reparentable) n).getNewParent();
			toInsert.fillInMissingKids(AST);
			parent.replaceKid(n, (Node) toInsert);
			
			return true;
		}
		
		return false;
	}

}
