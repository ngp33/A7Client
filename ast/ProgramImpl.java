package ast;

import mutation.Mutation;
import mutation.MutationImpl;

import java.util.Random;

/**
 * A data structure representing a critter program.
 *
 */
public class ProgramImpl extends Manykids implements Program {

	/**
	 * Initializes the programimpl
	 * @param args	the rules that will go into the ProgramImpl.
	 */
	Random R = new Random();
	String Mutationtype = ""; //this is what we will print for the type of mutation. 
	//Mutation m = new MutationImpl(); TODO make a mutation object
	
	public ProgramImpl(Rulesll r){
		children = r.toarray();
	}

    public ProgramImpl() {}

	@Override
    public Program mutate() {
        // TODO figure out a way to know what mutations a node supports
		int p = R.nextInt(size());
		//Program f = mutate(p,) The mutation (see above comment)
		//assign a new value to mutationtype
        return this;
    }

    @Override
    public Program mutate(int index, Mutation m) {
    	m.Mutate(nodeAt(index));
        return this;
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

	@Override
	Manykids getRootCopy() {
		return new ProgramImpl();
	}

}
