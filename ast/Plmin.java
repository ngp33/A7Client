package ast;

public class Plmin extends Twokids implements Expr{
	
	Boolean addition;
	
	public Plmin(Expr num1, Expr num2, Boolean add){
		addition = add;
		left = num1;
		right = num2;
		symbol = makenice();
	}

	/*
	@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		left.prettyPrint(sb);
		sb.append((addition) ? "+" : "-");
		right.prettyPrint(sb);
		return sb;
	}*/
	
	private String makenice(){
		return (addition) ? "+" : "-";
	}

	/*@Override
	public int value() {
		return (addition) ? (left.value() + right.value()) : (left.value() - right.value();
	}*/
}
