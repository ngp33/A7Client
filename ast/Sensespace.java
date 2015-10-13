package ast;

public class Sensespace extends Senses implements mutation.Removable {
	
	public Sensespace(int which, Expr where) {
		super(which);
		only = where;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public StringBuilder prettyPrint(StringBuilder sb){
		sb.append(pres.toString() + "[ ");
		only.prettyPrint(sb);
		sb.append("] ");
		return sb;
		
	}
	
	public Node nodeAt(int index){
		if (index == 0){
			return this;
		}
		return (only.nodeAt(index-1));
	}
	
	public int size(){
		return only.size() + 1;
	}
	
	public Node getReplacement() {
		return only;
	}

}
