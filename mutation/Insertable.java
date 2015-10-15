package mutation;

import ast.Program;

public interface Insertable {
	
	public void fillInMissingKids(Program possibleKids);
	
}
