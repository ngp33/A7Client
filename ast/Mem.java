package ast;

public class Mem extends Onekid implements Expr{
	
	public Mem(Expr e){
		only = e;
	}

	@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		// TODO Auto-generated method stub
		sb.append("mem[ ");
		only.prettyPrint(sb);
		sb.append("] ");
		return sb;
	}

}
