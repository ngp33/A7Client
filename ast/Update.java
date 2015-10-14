package ast;

public class Update extends Twokids implements Node, mutation.Removable {
	
	Update next;
	
	public Update(MemToUpdate andm, Expr ern){
		left = andm;
		right = ern;
		next = null;
		symbol = ":=";
	}
	
	public Update(Update n) {
		next = n;
	}

	@Override
	Twokids getRootCopy() {
		return new Update(next);
	}
	
	public Node getReplacement() {
		return null;
	}

}
