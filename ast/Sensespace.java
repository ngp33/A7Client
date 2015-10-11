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
	
	public Node nodeAt(int index){
		if (index == 0){
			return this;
		}
		return (space.nodeAt(index-1));
	}
	
	public int size(){
		return space.size() + 1;
	}

}
