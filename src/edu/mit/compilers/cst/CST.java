package edu.mit.compilers.cst;

import java.util.ArrayList;
import java.util.Iterator;

import antlr.Token;
import edu.mit.compilers.utils.TokenUtils;
/*
 * Concrete tree
 */
public class CST {

	private CSTNode root;

	public CST() {
		root = new CSTNode();
	}
	
	public CSTNode getRoot() {
		return root;
	}
	
	/*
	 * Get the instance (single instance design pattern)
	 */
	public static CST getInstance() {
		return new CST();
	}
	
	/*
	 * Show the CST
	 */
	public String showTree() {
		StringBuilder ret = new StringBuilder("");
		assert(root.getParent() == null);
		assert(root.getChildren() != null);
//		System.out.println(root.getDebugID());
//		System.out.println(root.getChildren().size());

		assert(root.getChildren().size() == 1);
		showTreeWithPrefix(root.getChildren().get(0), "", ret);
		return ret.toString();
	}

	/*
	 * Prune the tree
	 */
	public void PrunTree() {
		prune(root);
	}
	
	/*
	 * Implement the show func
	 */
	private void showTreeWithPrefix(CSTNode node, String prefix, StringBuilder result) {
		result.append(prefix + node.toString() + "\n");
		ArrayList<CSTNode> children = node.getChildren();
		if (children == null || children.isEmpty()) {
			return;
		}
		
		// show the children
		Iterator<CSTNode> iter = children.iterator();
		while (iter.hasNext()) {
			CSTNode child = (CSTNode) iter.next();
			showTreeWithPrefix(child, prefix + "  ", result);
		}
	}
	
	private void prune(CSTNode now) {
		ArrayList<CSTNode> prunedChildren = new ArrayList<CSTNode>();
		Iterator<CSTNode> iter = now.getChildren().iterator();
		
		while (iter.hasNext()) {
			CSTNode node = iter.next();
			Token token = node.getToken();
			
			// Maybe useless
			if (!node.hasChild() && TokenUtils.isUseless(token)) {  
				continue;
			}
			prune(node);
			prunedChildren.add(node);
		}
		
		now.setChildren(prunedChildren);
	}
	
}