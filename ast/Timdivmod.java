package ast;

public class Timdivmod extends Twokids implements Expr {
	
	Boolean mult;
	
	public Timdivmod(Expr one, Expr two, Boolean times){
		left = one;
		right = two;
		mult = times;
		symbol = makenice();
	}

	/*@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		// TODO Auto-generated method stub
		left.prettyPrint(sb);
		if (mult == null){
			sb.append("mod ");
		}
		else{
			sb.append(mult ? "* " : "/ ");
		}
		right.prettyPrint(sb);
		return sb;
	}*/

	private String makenice() {
		if (mult == null){
			return ("mod");
		}
		return (mult ? "*" : "/");
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
