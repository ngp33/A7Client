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
	
	@Override
	public void transform() {
		Random rand = new Random();
		
		link = MathOperator.values()[rand.nextInt(5)];
	}
	
	@Override
	public mutation.Insertable getNewParent() {
		mutation.Insertable newParent;
		Random rand = new Random();
		
		int selector = rand.nextInt(3);
		if (selector == 0) {
			newParent = new MathOp();
			((MathOp) newParent).op = MathOperator.values()[rand.nextInt(5)];
			if (rand.nextBoolean()) {
				((MathOp) newParent).left = this;
			} else {
				((MathOp) newParent).right = this;
			}
		} else if (selector == 1) {
			newParent = new MemAccess(this);
		} else {
			newParent = new Sensespace(rand.nextInt(4) + 1, this);
		}
		
		return newParent;
	}
	
}
