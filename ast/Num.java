package ast;

public class Num implements Expr {
	int val;
	public Num(int anum){
		val = anum;
	}
	
	/*@Override
	public int value(){
		return val;
		
	}*/

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
		sb.append(val + " ");
		return sb;
	}

}
