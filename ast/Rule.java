package ast;

/**
 * A representation of a critter rule.
 */
//TODO give the rule some time of arrow representation.
public class Rule implements Node {
	private Condition one;
	private Node two;
	public Rule next = null;
	
	public Rule(Condition a, Node b){
		one = a;
		two = b;
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
        one.prettyPrint(sb);
        sb.append("-->");
        two.prettyPrint(sb);
        return sb;
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }
}
