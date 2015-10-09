package ast;

public abstract class Onekid implements Node{
	
	Expr only;
	
	
	public Node Nodeat(int place) throws IndexOutOfBoundsException{
		if (place == 0){
			return this;
		}
		return only.nodeAt(place-1);
	}

}
