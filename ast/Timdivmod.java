package ast;

public class Timdivmod implements Expr {
	Expr left;
	Expr right;
	Boolean mult;
	
	public Timdivmod(Expr one, Expr two, Boolean times){
		left = one;
		right = two;
		mult = times;
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
		left.prettyPrint(sb);
		if (mult == null){
			sb.append("mod");
		}
		else{
			sb.append(mult ? "*" : "/");
		}
		right.prettyPrint(sb);
		return sb;
	}

	/*@Override
	public int value() {
		// TODO Auto-generated method stub
		if (mult == null){
			return left.value() % right.value();
		}
		else if (mult){
			return left.value()*right.value();
		}
		else{
			return left.value()/right.value(); //this only returns the integer division. hopefully this isnt a problem
		}
	}*/

}
