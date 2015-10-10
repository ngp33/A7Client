package ast;

public class Updateact extends Manykids implements Node {

	public Updateact(Update [] u, Action a){
		children = (a == null) ? new Node[u.length] : new Node [u.length + 1];
		for (int place = 0; place < u.length; place++){
			children[place] = u[place];
		}
		if (a != null){
			children[u.length] = a;
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
	
	//It looks like part of the problem is that the prettyPrint on updateAction prints its children and not the semicolon.
	
	public StringBuilder prettyPrint(StringBuilder sb){
		for (Node them : children){
			them.prettyPrint(sb);
			sb.append("  ");
		}
		sb.insert(sb.length()-3,";"); // the two here is pretty arbitrary, not quite sure how to make this work/wh
		return sb;//it does sometimes
	}

}
		
