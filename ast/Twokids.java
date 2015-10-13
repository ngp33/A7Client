package ast;

public abstract class Twokids implements Node{
	protected Node left;
	protected Node right;
	protected Object link;
	protected String symbol;
	
	public StringBuilder prettyPrint(StringBuilder sb) {
		left.prettyPrint(sb);
		sb.append(symbol); //maybe figure out a way to make this lowercase?
		sb.append(" ");
		right.prettyPrint(sb);
		return sb;
	}
	
	public int size(){
		return left.size() + right.size() + 1;
	}
	
	public Node nodeAt(int in){
		int i = left.size();
		
		if (in == 0){
			return this;
		}
		if (in <= i){
			return left.nodeAt(in-1);
		}
		else{
			return right.nodeAt(in-i-1);
		}
	}
	
	abstract Twokids getRootCopy();
	
	public Node copy() {
		Twokids clone = getRootCopy();
		
		clone.left = left.copy();
		clone.right = right.copy();
		clone.link = link;
		clone.symbol = symbol;
		
		return clone;
	}

}
