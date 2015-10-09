package ast;

public abstract class Nokids implements Node{
	
	
	
	public Node Nodeat(int n) throws IndexOutOfBoundsException{
		if (n == 0){
			return this;
		}
		throw new IndexOutOfBoundsException();
	}
}
