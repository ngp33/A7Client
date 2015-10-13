package mutation;

import ast.Manykids;
import ast.Node;

public class MutDuplicate extends MutationImpl {

	@Override
	public boolean equals(Mutation m) {
		// TODO Auto-generated method stub
		return false;
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
