package ast;

public class Mem extends Onekid implements Expr {
	
	public Mem(Expr e){
		only = e;
	}
	
	public Mem() {}

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
		return new Mem();
	}
	
	public void fillInMissingKids(Program possibleKids) {
		//Just to get rid of the error for now. We're removing this class soon.
	}

}
