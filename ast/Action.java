package ast;

public class Action implements Node {
	Hamlet type;
	Expr e = null;
	
	public Action(Hamlet input){
		type = input;
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public Node nodeAt(int index) throws IndexOutOfBoundsException {
		if (index == 0){
			return this;
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		sb.append(type.toString());
		return sb;
	}
	
	public enum Hamlet{
		wait, forward, backward, left, right, eat, attack,
		grow, bud, mate, tag, serve
	};

}
