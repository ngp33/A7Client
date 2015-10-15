package mutation;

import ast.Manykids;
import ast.Node;
import ast.Program;

public class MutDuplicate extends MutationImpl {
	
	public MutDuplicate(Program tree) {
		type = MutationType.duplicate;
		AST = tree;
	}
	
	public MutDuplicate() {
		type = MutationType.duplicate;
	}

	@Override
	public boolean Mutate(Node n) {
		// TODO Auto-generated method stub
		
		if (n instanceof Manykids) {
			Node copy = ((Manykids) n).getRandomKidCopy();
			
			if (copy == null) {
				return false;
			} else {
				((Manykids) n).addKid(copy);
				return true;
			}
		}
		
		return false;
	}

}
