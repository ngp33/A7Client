package ast;

public class MemAccess extends MemToUpdate implements Expr, mutation.Removable {
	
	public MemAccess() {}
	
	public MemAccess(MemToUpdate mem){
		only = mem.only;
	}
	
	@Override
	Onekid getRootCopy() {
		return new MemAccess();
	}
	
	public Node getReplacement() {
		return only;
	}

}
