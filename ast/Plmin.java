package ast;

public class Plmin implements Expr{
	
	Boolean addition;
	Expr left;
	Expr right;
	
	public Plmin(Expr num1, Expr num2, Boolean add){
		addition = add;
		left = num1;
		right = num2;
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
		left.prettyPrint(sb);
		sb.append((addition) ? "+" : "-");
		right.prettyPrint(sb);
		return sb;
	}

	/*@Override
	public int value() {
		if (addition){
			return left.value() + right.value();
		}
		return left.value()-right.value();
	}*/

}
