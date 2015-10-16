package mutation;

import ast.Manykids;
import ast.Node;
import ast.Onekid;
import ast.Specaction;
import ast.Twokids;

public abstract class ParentConsciousMutation extends MutationImpl {
	
	public Node parent;
	
	//Will add methods depending on how mutations are implemented in the main program
	
	
	public int findparent(Node root, int place){
		final Node child = root.nodeAt(place);
		boolean found = false;
		Node potentialfather = null;
		while (!found){
			potentialfather = root.nodeAt(place - 1);
			if (potentialfather instanceof Onekid){
				found = onekid((Onekid) potentialfather, child);
			}
			else if (potentialfather instanceof Twokids){
				found = twos((Twokids) potentialfather, child);
			}
			else if (potentialfather instanceof Manykids){
				found = plural((Manykids) potentialfather, child);
			}
			else if (potentialfather instanceof Specaction){
				found = ridiculous((Specaction) potentialfather, child);
			}
			place --;
		}
		parent = potentialfather;
		return place;
	}
	
	
	private boolean onekid(Onekid dad, Node child){
		if (dad.only == child){
			return true;
		}
		return false;
	}
	
	private boolean twos(Twokids dad, Node child){
		if (dad.left == child  || dad.right == child){
			return true;
		}
		return false;
	}
	
	private boolean plural(Manykids dad, Node child){
		for (int place = 0; place < dad.children.length; place++ ){
			if (dad.children[place] == child){
				return true;
			}
		}
		return false;
	}
	
	private boolean ridiculous(Specaction dad, Node child){
		if (dad.eval == child){
			return true;
		}
		return false;
	}
}
