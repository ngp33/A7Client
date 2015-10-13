package ast;

public class Update extends Twokids implements Node {
	
	Update next;
	
	public Update(Mem memories, Expr ern){
		left = memories;
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

}
