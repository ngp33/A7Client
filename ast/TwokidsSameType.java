package ast;

import java.util.Random;

import ast.BinaryCondition.Operator;

public abstract class TwokidsSameType extends Twokids implements mutation.Swappable,
mutation.Transformable, mutation.Insertable, mutation.Reparentable {

	@Override
	public void swapKids() {
		Node temp = left;
		left = right;
		right = temp;
	}
	
	@Override
	public void fillInMissingKids(Program possibleKids) {
		if (left == null) {
			left = possibleKids.getRandomNode(Expr.class);
		} else if (right == null) {
			right = possibleKids.getRandomNode(Expr.class);
		}
	}
	
	public mutation.Insertable getNewParent() {
		BinaryCondition newParent = new BinaryCondition();
		
		Random rand = new Random();
		
		if (rand.nextBoolean()) {
			newParent.left = this;
		} else {
			newParent.right = this;
		}
		
		newParent.link = rand.nextBoolean() ? Operator.OR : Operator.AND;
		
		return newParent;
	}

}
