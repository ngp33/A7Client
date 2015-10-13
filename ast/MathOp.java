package ast;

import java.util.Random;

public class MathOp extends TwokidsSameType implements Expr, mutation.Removable {

	MathOperator op;
	
	public MathOp(Expr one, Expr two, MathOperator o){
		left = one;
		right = two;
		op = o;
		symbol = makenice();
	}
	
	public MathOp() {}
	
    public StringBuilder prettyPrint(StringBuilder sb) {
    	sb.append("( ");
    	super.prettyPrint(sb);
    	sb.append(") ");
    	
    	return sb;
    }
	
	public String makenice() {
		switch (op) {
			case add: return "+";
			case sub: return "-";
			case mult: return "*";
			case div: return "/";
			case mod: return "mod";
		}
		return null;
	}
	
	public enum MathOperator {
		add, sub, mult, div, mod
	}

	@Override
	Twokids getRootCopy() {
		return new MathOp();
	}
	
	public Node getReplacement() {
		Random rand = new Random();
		
		return rand.nextBoolean() ? left : right;
	}
}
