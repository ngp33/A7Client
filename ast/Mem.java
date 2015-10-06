package ast;

public class Mem implements Expr{
	
	Expr finders;
	
	public Mem(Expr e){
		finders = e;
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
		sb.append("mem[");
		finders.prettyPrint(sb);
		sb.append("]");
		return sb;
	}

}
