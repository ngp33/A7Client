package ast;

/**
 * A data structure representing a critter program.
 *
 */
public class ProgramImpl extends Manykids implements Program {

	/**
	 * Initializes the programimpl
	 * @param args	the rules that will go into the ProgramImpl.
	 */
	public ProgramImpl(Rulesll r){
		children = r.toarray();
	}

    @Override
    public Program mutate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Program mutate(int index, Mutation m) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StringBuilder prettyPrint(StringBuilder sb) {
        for (Node them : children){
        	them.prettyPrint(sb);
        	sb.append("\n");
        }
        return sb;
    }
    
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }
    
    /**Returns the list of all the nodes in the AST. The idea is that
     * this will help when it comes time to randomly pick a node to mutate
     * @return
     * 		Node[]
     */
    private Node [] thelist(){
    	Node [] n = new Node [size()];
    	for (int place = 0; place < size(); place ++){
    		n[place] = nodeAt(place);
    	}
    	return n;
    }

}
