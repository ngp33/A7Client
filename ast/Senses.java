package ast;

import java.util.Random;

import ast.MathOp.MathOperator;

public class Senses extends Onekid implements Expr, mutation.Transformable, mutation.Reparentable {
	
	protected six pres;
	
	public Senses(int which){
		if (which == 1){
			pres = six.ahead;
		}
		else if (which == 2){
			pres = six.nearby;
		}
		else{
			pres = which == 3 ? six.random : six.smell; //I believe that true means random, and I will act under that assumption
		}
	}
	
	public Senses(six p) {
		pres = p;
	}

	@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		sb.append(pres.toString() + " ");
		return sb;
	}
	
	public enum six{
		nearby, ahead, random, smell
	}

	@Override
	public int size() {
		return 1;
	}

	@Override
	public Node nodeAt(int index) throws IndexOutOfBoundsException {
		if (index == 0){ return this;}
		throw new IndexOutOfBoundsException();
	}

	@Override
	Onekid getRootCopy() {
		return new Senses(pres);
	}
	
	@Override
	public Node copy() {
		return getRootCopy();
	}
	
	@Override
	public void transform() {
		//Nothing to change smell to without adding a child
	}
	
	//Cringe x2.
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
	
	public Node getRandomReplacement(Program possibleKids, Node[] ignoreList) {
		return possibleKids.getRandomNode(Expr.class, ignoreList);
	}

}
