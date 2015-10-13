package ast;

public class Action extends Nokids implements Node {
	Hamlet type;
	
	public Action(Hamlet input){
		type = input;
	}

	@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		sb.append(type.toString() + " ");
		return sb;
	}
	
	public enum Hamlet {
		wait, forward, backward, left, right, eat, attack,
		grow, bud, mate, tag, serve
	}

	@Override
	public Node copy() {
		return new Action(type);
	}

}
