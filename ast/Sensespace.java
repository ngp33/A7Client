package ast;

public class Sensespace extends Senses {
	private Expr space;

	public Sensespace(int which, Expr where) {
		super(which);
		space = where;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public StringBuilder prettyPrint(StringBuilder sb){
		sb.append(pres.toString() + "[ ");
		space.prettyPrint(sb);
		sb.append("] ");
		return sb;
		
	}

}
