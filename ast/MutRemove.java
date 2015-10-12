package ast;

public class MutRemove implements Mutation {

	@Override
	public boolean equals(Mutation m) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Mutate(Node n) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/** takes a Node and mutates it according to rule 1 of mutations*/
	//I think the biggest deciding factor for how this mutation affects a node is
	//The node's primary subclass of Node (eg. expression, Condition etc) and whether the
	//node's children evaluate the same way it does.

}
