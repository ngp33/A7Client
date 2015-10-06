package ast;

/**
 * A representation of a binary Boolean condition: 'and' or 'or'
 *
 */
public class BinaryCondition implements Condition {
	Condition left;
	Condition right;
	Operator bin;

    /**
     * Create an AST representation of l op r.
     * @param l
     * @param op
     * @param r
     */
    public BinaryCondition(Condition l, Operator op, Condition r) {
        //TODO
    	left = l;
    	right = r;
    	bin = op;
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
    	left.prettyPrint(sb).toString();
    	sb.append((bin == Operator.OR) ? "or" : "and");
    	right.prettyPrint(sb).toString();
        return sb;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * An enumeration of all possible binary condition operators.
     */
    public enum Operator {
        OR, AND;
    }

	/*@Override
	public Boolean getval() {
		// TODO Auto-generated method stub
    	if (bin.equals(Operator.AND)){
    		if (left.getval() && right.getval()){
    			return true;
    		}
    		return false;
    	}
    	else{
    		if (left.getval() || right.getval()){
    			return true;
    		}
    		return false;
    	}
	}*/
}
