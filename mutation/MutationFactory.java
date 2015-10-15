package mutation;

/**
 * A factory that produces the public static Mutation objects corresponding to each
 * mutation
 */
import java.util.Random;

public class MutationFactory {
	
	
	
    public static Mutation getRemove() {
        return new MutRemove();
    }

    public static Mutation getSwap() {
        return new MutSwap();
    }

    public static Mutation getReplace() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    public static Mutation getTransform() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    public static Mutation getInsert() {
        return new MutInsert();
    }

    public static Mutation getDuplicate() {
    	return new MutDuplicate();
    }
    
    /** Returns an array of all the mutation objects*/
    public static Mutation[] allMuts(){
    	Mutation mutone = getRemove();
    	Mutation muttwo = getSwap();
    	Mutation mutthree = getReplace();
    	Mutation mutfour = getTransform();
    	Mutation mutfive = getInsert();
    	Mutation mutsix = getDuplicate();
    	return new Mutation [] {mutone, muttwo,
    			mutthree, mutfour, mutfive, mutsix
    	};
    	
    }
    
    /** effect: randomizes an array of mutation objects*/
    public static void randMutation(Mutation [] total){
    	Random rand = new Random();
    }
}
