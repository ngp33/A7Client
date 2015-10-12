package ast;

/**
 * A representation of a binary Boolean condition: 'and' or 'or'
 *
 */
public class BinaryCondition extends Twokids implements Condition{

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
    	link = op;
    	symbol = makenice();
    }


    
    /*@Override
    public StringBuilder prettyPrint(StringBuilder sb) {
        // TODO Auto-generated method stub
    	left.prettyPrint(sb).toString();
    	sb.append((link == Operator.OR) ? "or " : "and ");
    	right.prettyPrint(sb).toString();
        return sb;
    }*/
    
    public StringBuilder prettyPrint(StringBuilder sb) {
    	sb.append("{ ");
    	super.prettyPrint(sb);
    	sb.append("} ");
    	
    	return sb;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /** represents the operator in a nice printable way*/
    public String makenice(){
    	return (link == Operator.OR) ? "or" : "and";
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
