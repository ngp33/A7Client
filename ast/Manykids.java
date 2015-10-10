package ast;

public abstract class Manykids implements Node {
	Node [] children;
	
	
	public Node nodeAt (int there){
		int bigness = 0;
		int place = -1;
		if (there == 0){
			return this;
		}
		while (bigness < there){
			place ++;
			bigness += children[place].size();
		}
		return children[place].nodeAt(there-place-1);
	}
	
	public int size(){
		int bigness = 1;
		for (Node them : children){
			bigness += them.size();
		}
		return bigness;
	}

}
