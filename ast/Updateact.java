package ast;

public class Updateact extends Manykids implements Node {

	public Updateact(Update [] u, Action a){
		/*children = (a == null) ? new Node[u.length] : new Node [u.length + 1];
		for (int place = 0; place < u.length; place++){
			children[place] = u[place];
		}
		if (a != null){
			children[u.length] = a;
		}*/
		
		//If blocks are pretty slow so I just rewrote it with 1 instead of two
		
		if (a == null) {
			children = new Node[u.length];
		} else {
			children = new Node[u.length+1];
			children[u.length] = a;
		}
		for (int place = 0; place < u.length; place++) {
			children[place] = u[place];
		}
	}
	
	/*public Update[] getupdates(){
		return u;
	}
	public Action getaction(){
		return a;
	}
	
	
	@Override
	public StringBuilder prettyPrint(StringBuilder sb) {
		if (u.length != 0){
			for (Update them : u){
				them.prettyPrint(sb);
			}
		}
		if ( a != null){
			a.prettyPrint(sb);
		}
		return sb;
	}*/
		
	public StringBuilder prettyPrint(StringBuilder sb) {
		children[0].prettyPrint(sb);
		
		for (int i = 1; i < children.length; i++) {
			sb.append("\n\t"); //Adding spaces to line it up with the first update seems unnecessary to keep it pretty.
							   //I just added a tab.
			children[1].prettyPrint(sb);
		}
		
		sb.append(";");
		return sb;
	}

}
		
