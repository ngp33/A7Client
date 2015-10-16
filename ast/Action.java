package ast;

import java.util.Random;

public class Action extends Nokids implements Node, mutation.Removable, mutation.Transformable {
	
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
	
	public Node getReplacement() {
		return null;
	}

	@Override
	public void transform() {
		Random rand = new Random();
		
		type = Hamlet.values()[rand.nextInt(10)];
	}
	
	@Override
	public Node getRandomReplacement(Program possibleKids, Node[] ignoreList) {
		return possibleKids.getRandomNode(Action.class, ignoreList);
	}

}
