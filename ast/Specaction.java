package ast;

public class Specaction extends Action {
	Expr eval;

	public Specaction(Hamlet input, Expr e) {
		super(input);
		eval = e;
	}
	
	public StringBuilder prettyPrint(StringBuilder sb){
		super.prettyPrint(sb);
		sb.append("[ ");
		eval.prettyPrint(sb);
		sb.append("] ");
		return sb;
	}
	
	public int size(){
		return eval.size()+1;
	}
	
	public Node nodeAt(int index){
		if (index == 0){
			return this;
		}
		return (eval.nodeAt(index-1));
	}
	//TODO override methods,

}
