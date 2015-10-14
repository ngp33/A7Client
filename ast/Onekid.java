package ast;

public abstract class Onekid implements Expr { //I changed this to Expr from Node. I don't think itll hurt...
	
	Expr only;
	
	
	public Node nodeAt(int place) throws IndexOutOfBoundsException{
		if (place == 0){
			return this;
		}
		return only.nodeAt(place-1);
	}
	
	public int size(){
		return only.size() + 1;
	}
	
	abstract Onekid getRootCopy();
	
	public Node copy() {
		Onekid clone = getRootCopy();
		clone.only = (Expr) only.copy();
		
		return clone;
	}
	
	public void replaceKid(Node old, Node replacement) {
		only = (Expr) replacement;
	}

}
