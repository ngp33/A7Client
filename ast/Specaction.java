package ast;

import java.util.Random;

import world.Critter;

public class Specaction extends Action {
	public Expr eval;

	public Specaction(Hamlet input, Expr e) {
		super(input);
		eval = e;
	}
	
	public StringBuilder prettyPrint(StringBuilder sb){
		super.prettyPrint(sb);
		sb.append("[ ");
		eval.prettyPrint(sb);
		sb.append("] ");
		return sb;
	}
	
	public int size(){
		return eval.size()+1;
	}
	
	public Node nodeAt(int index){
		if (index == 0){
			return this;
		}
		return (eval.nodeAt(index-1));
	}
	
	public Node copy() {
		return new Specaction(type, (Expr) eval.copy());
	}

	@Override
	public void transform() {
		Random rand = new Random();
		
		type = Hamlet.values()[rand.nextInt(2)+10];
	}
	
	public void replaceKid(Node old, Node replacement) {
		eval = (Expr) replacement;
	}
	
	public void commit(Critter c){
		if (type.equals(Hamlet.serve)) {
			if (Actionpacked.checkempty(c, true)) {
				c.w.putFood(eval.value(c), Actionpacked.dircoords(c, true));
			}
			c.mem[4] -= eval.value(c);
		}
		else if (type.equals(Hamlet.tag)) {
			int [] place = Actionpacked.dircoords(c, true);
			if (c.w.getNumRep(place) > 0) {
				Critter other = (Critter) c.w.getHex(place[0], place[1]);
				other.mem[6] = eval.value(c);
			}
			c.mem[4] -= c.mem[3];	
		}
		//TODO handle when the request is tag, not serve
	}
	
}
