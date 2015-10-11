package ast;

/**
 * A representation of a critter rule.
 */
//TODO give the rule some time of arrow representation.
public class Rule extends Twokids implements Node {
	
	public Rule next = null;
	
	public Rule(Condition a, Node b){
		left = a;
		right = b;
		symbol = "-->";
	}
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }
}
