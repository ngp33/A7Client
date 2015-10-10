package ast;

public abstract class Nokids implements Node{
	
	
	
	public Node nodeAt(int n) throws IndexOutOfBoundsException{
		if (n == 0){
			return this;
		}
		throw new IndexOutOfBoundsException();
	}
	
	public int size(){
		return 1;
	}
}
