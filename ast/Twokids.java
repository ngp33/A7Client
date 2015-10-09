package ast;

public abstract class Twokids implements Node{
	protected Node left;
	protected Node right;
	protected Object link;
	protected String symbol;
	
	public StringBuilder prettyPrint(StringBuilder sb){
		sb.append(left.prettyPrint(sb));
		sb.append(symbol); //maybe figure out a way to make this lowercase?
		sb.append(" ");
		sb.append(right.prettyPrint(sb));
		return sb;
	}
	
	public int size(){
		return left.size() + right.size() + 1;
	}
	
	public Node nodeAt(int in){
		if (in == 0){
			return this;
		}
		if (left.size() < in){
			return left.nodeAt(in-1);
		}
		else{
			return right.nodeAt(in-left.size()-1);
		}
	}

}
