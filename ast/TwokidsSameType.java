package ast;

public abstract class TwokidsSameType extends Twokids implements mutation.Swappable {

	@Override
	public void swapKids() {
		Node temp = left;
		left = right;
		right = temp;
	}

}
