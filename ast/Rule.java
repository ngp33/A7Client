package ast;

/**
 * A representation of a critter rule.
 */
//TODO give the rule some time of arrow representation.
public class Rule extends Twokids implements Node, mutation.Removable {
	
	public Rule next = null;
	
	public Rule(Condition a, Updateact b){
		left = a;
		right = b;
		symbol = "-->";
	}
	
	public Rule(Rule n) {
		next = n;
	}
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	Twokids getRootCopy() {
		return new Rule(next);
	}
	
	public Node getReplacement() {
		return null;
	}
	
	public Node getRandomReplacement(Program possibleKids) {
		return possibleKids.getRandomNode(Rule.class);
	}
	
}
