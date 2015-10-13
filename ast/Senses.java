package ast;

public class Senses extends Onekid implements Expr {
	
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

}
