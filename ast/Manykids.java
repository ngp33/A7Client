package ast;

public abstract class Manykids {
	Node [] children;
	
	
	public Node nodeAt (int there){
		int bigness = 0;
		int place = -1;
		while (bigness < there){
			place ++;
			bigness += children[place].size();
		}
		return children[place].nodeAt(there-bigness-1);
	}

}
