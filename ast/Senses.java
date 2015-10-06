package ast;

public class Senses implements Expr {
	private six pres;
	
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

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Node nodeAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		// TODO Auto-generated method stub
		sb.append(pres.toString());
		return sb;
	}
	public enum six{
		nearby, ahead, random, smell
	};

}
