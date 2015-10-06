package ast;

public class Action implements Node {
	Hamlet type;
	Expr e = null;
	
	public Action(Hamlet input){
		type = input;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Node nodeAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		// TODO Auto-generated method stub
		sb.append(type.toString()); //might not work?
		return sb;
	}
	
	public enum Hamlet{
		wait, forward, backward, left, right, eat, attack,
		grow, bud, mate, tag, serve
	};

}
