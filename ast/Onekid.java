package ast;

public abstract class Onekid implements Node{
	
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

}
