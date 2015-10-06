package ast;

public class Updateact implements Node {
	Update [] u;
	Action a = null;
	public Updateact(Update [] u, Action a){
		this.u = u;
		this.a = a;
	}
	
	public Update[] getupdates(){
		return u;
	}
	public Action getaction(){
		return a;
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Node nodeAt(int index) {
		// TODO Auto-generated method stub
		return null;
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
	}
}
		
