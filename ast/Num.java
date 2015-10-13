package ast;

public class Num extends Nokids implements Expr {
	
	int val;
	
	public Num(int anum){
		val = anum;
	}
	
	/*@Override
	public int value(){
		return val;
		
	}*/

	@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		sb.append(val + " ");
		return sb;
	}

	@Override
	public Node copy() {
		return new Num(val);
	}

}
