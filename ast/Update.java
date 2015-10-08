package ast;

public class Update implements Node{
	Mem m;
	Expr e;
	Update next;
	
	public Update(Mem memories, Expr ern){
		m = memories;
		e = ern;
		next = null;
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
		m.prettyPrint(sb);
		sb.append(":= ");
		e.prettyPrint(sb);
		return sb;
	}
	

}
