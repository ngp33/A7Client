package ast;

import java.util.Random;

import mutation.Mutation;
import mutation.MutationFactory;
import mutation.MutationImpl;
import mutation.ParentConsciousMutation;

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
	Mutation [] muttypes = MutationFactory.allMuts(this);
	String Mutationtype = ""; //this is what we will print for the type of mutation. 
	Random R = new Random();
	
	public ProgramImpl(Rulesll r){
		children = r.toarray();
	}

    public ProgramImpl() {}

	@Override
    public Program mutate() {
        
		int p = R.nextInt(size());
		MutationFactory.randMutation(muttypes);
		int place = 0;
		while (usedMutate(p, muttypes[place])){
		}
		//TODO update the Mutationtype string
		Mutationtype = muttypes[place].type();
        return this;
    }

    @Override
    public Program mutate(int index, Mutation m) {
    	usedMutate(index, m);
        return this;
    }
    
    /** executes mutation m on the node and returns true if the mutation was successful*/
    public boolean usedMutate(int index, Mutation m) {
    	if (m instanceof ParentConsciousMutation) {
    		((ParentConsciousMutation) m).findparent(this, index);
    	}
    	return m.Mutate(nodeAt(index));
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
	
	public Node getRandomNode(Class<?> type) {
		int size = this.size();
		int[] indexOrder = new int[size];
		
		for (int i = 0; i < size; i++) {
			indexOrder[i] = i;
		}
		
		Random rand = new Random();
        
        int j;
        int temp;
        for (int i = size-1; i > 0; i--) {
        	j = rand.nextInt(i);
        	temp = indexOrder[j];
        	indexOrder[j] = indexOrder[i];
        	indexOrder[i] = temp;
        }

        for (int i : indexOrder) {
        	Node n = this.nodeAt(i);
        	
        	if (type.isInstance(n)) {
        		return n;
        	}
        }
		
		return null;
	}

}
