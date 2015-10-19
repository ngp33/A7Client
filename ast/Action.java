package ast;

import java.util.Random;

import world.Critter;

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
	public Node getRandomReplacement(Program possibleKids) {
		return possibleKids.getRandomNode(Action.class);
	}

	public void commit(Critter c) {
		if (type.equals(Hamlet.attack)){ //Not sure if this is the way to do this for enums
			
		}
		if (c.mem[4] <= 0){
			Actionpacked.dies(c);
		}
		// TODO Auto-generated method stub
	}
	public void commit(Critter c, Critter victim){
		Actionpacked.attack(c,victim);
	}
	

}
