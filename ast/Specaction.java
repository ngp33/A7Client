package ast;

public class Specaction extends Action{
	Expr eval;

	public Specaction(Hamlet input, Expr e) {
		super(input);
		eval = e;
	}
	
	public StringBuilder prettyPrint(StringBuilder sb){
		super.prettyPrint(sb);
		sb.append("[");
		eval.prettyPrint(sb);
		sb.append("]");
		return sb;
	}
	//TODO override methods,

}
