package ast;

public class Booly implements Condition {
	Expr left;
	Expr right;
	equalities rel;
	
	public Booly(Expr one, Expr two, equalities e){
		left = one;
		right = two;
		rel = e;
	}
	
	//somehow make it so this always assigns values to left and right and rel that work to be true or false depending on b.
	public Booly(Boolean b){
		//TODO
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
		if (rel.equals(equalities.LE) || rel.equals(equalities.LT)){
			sb.append("<");
		}
		else if (rel.equals(equalities.GE) || rel.equals(equalities.GT)){
			sb.append(">");
		}
		else if (rel.equals(equalities.NE)){
			sb.append("!");
		}
		if (!(rel.equals(equalities.LT) || rel.equals(equalities.GT))){
			sb.append("= ");
		}
		else{
			sb.append(" ");
		}
		right.prettyPrint(sb);
		return sb;
	}

	/*@Override
	public Boolean getval() {
		if (rel.equals(equalities.LT)){
			return (left.value() < right.value());
		}
		else if (rel.equals(equalities.LE)){
			return (left.value() <= right.value());
		}
		else if (rel.equals(equalities.EQ)){
			return (left.value() == right.value());
		}
		else if (rel.equals(equalities.GT)){
			return (left.value() > right.value());
		}
		else if (rel.equals(equalities.GE)){
			return (left.value() >= right.value());
		}
		else if (rel.equals(equalities.NE)){
			return (left.value() != right.value());
		}
		System.out.println("what did you input for the rel value? It's not right");
		return false; // TODO make more useful.
	}*/
	
	public enum equalities{
		LT, LE, EQ, GT, GE, NE;
	}

}
