package edu.mit.compilers.cst;

import antlr.Token;
import edu.mit.compilers.utils.TokenUtils;

import java.util.ArrayList;
/*
 * Concrete tree
 */
public class CST {

	private final CSTNode root;

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
	public String show() {
		StringBuilder ret = new StringBuilder();
		assert(root.getParent() == null);
		assert(root.getChildren() != null);
//		System.out.println(root.getDebugID());
//		System.out.println(root.getChildren().size());

		assert(root.getChildren().size() == 1);
		showWithPrefix(root.getChildren().get(0), "", ret);
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
	private void showWithPrefix(CSTNode node, String prefix, StringBuilder result) {
		result.append(prefix).append(node.toString()).append("\n");
		ArrayList<CSTNode> children = node.getChildren();
		if (children == null || children.isEmpty()) {
			return;
		}
		
		// show the children
		for (CSTNode child : children) {
			showWithPrefix(child, prefix + "  ", result);
		}
	}
	
	private void prune(CSTNode now) {
		ArrayList<CSTNode> prunedChildren = new ArrayList<>();

		for (CSTNode node : now.getChildren()) {
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
