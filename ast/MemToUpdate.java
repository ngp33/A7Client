package ast;

import world.Critter;

public class MemToUpdate extends Onekid {
	
	public MemToUpdate(Expr e){
		only = e;
	}
	
	public MemToUpdate() {}

	@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		// TODO Auto-generated method stub
		sb.append("mem[ ");
		only.prettyPrint(sb);
		sb.append("] ");
		return sb;
	}

	@Override
	Onekid getRootCopy() {
		return new MemToUpdate();
	}

	@Override
	public Node getRandomReplacement(Program possibleKids) {
		return possibleKids.getRandomNode(MemToUpdate.class);
	}
	
	
	/** returns the value of mem[only] if only < mem.length. Otherwise returns -1*/
	public int value(Critter c){
		return (only.value() < c.mem[0] && 0 <= only.value()) ? c.mem[only.value()] : -1;
	}

}
