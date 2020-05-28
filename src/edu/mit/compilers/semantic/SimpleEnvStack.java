package edu.mit.compilers.semantic;
import java.util.Iterator;
import java.util.LinkedList;
import edu.mit.compilers.ir.IR;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRMemberDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;

public class SimpleEnvStack implements EnvStack {

	private class KeyValue {
		public String key;
		public IRMemberDecl value;
		public KeyValue(String key, IRMemberDecl value) {
			this.key = key;
			this.value = value;
		}
	};
	
	private LinkedList<KeyValue> stack = new LinkedList<KeyValue>();

	@Override
	public boolean pushMemberDecl(String identifier, IRMemberDecl decl) {
		stack.push(new KeyValue(identifier, decl));
		return true;
	}

	@Override
	public boolean popMemberDecl(String identifier) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean popMemberDecl(int count) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IR contain(String identifier) {
		// TODO Auto-generated method stub
		return null;
	}
	

}