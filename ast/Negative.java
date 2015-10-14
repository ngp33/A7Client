package ast;

public class Negative extends Onekid implements Expr {
	
	public Negative(Expr e){
		only = e;
	}
	
	public Negative(){}

	@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		sb.append("- ");
		only.prettyPrint(sb);
		return sb;
	}

	@Override
	Onekid getRootCopy() {
		return new Negative();
	}

}
