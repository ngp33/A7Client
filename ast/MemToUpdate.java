package ast;

public class MemToUpdate extends Onekid implements Expr {
	
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
	public Node getRandomReplacement(Program possibleKids, Node[] ignoreList) {
		return possibleKids.getRandomNode(MemToUpdate.class, ignoreList);
	}

}
