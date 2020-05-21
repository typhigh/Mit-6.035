package edu.mit.compilers.cst;

import java.util.ArrayList;
import java.util.Iterator;

import antlr.Token;
import edu.mit.compilers.ir.IRProgram;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRImportDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.statement.IRBlock;
import edu.mit.compilers.semantic.ArrayTypeDesc;
import edu.mit.compilers.semantic.BasicTypeDesc;
import edu.mit.compilers.semantic.TypeDesc;
import edu.mit.compilers.semantic.Variable;

public class CSTParser {

	public static IRProgram parseIRProgram(CST tree) {
		return parseIRProgram(tree.getRoot().getChild(0));
	}
	
	private static IRProgram parseIRProgram(CSTNode node) {
		IRProgram ret = new IRProgram();
		
		/// Parse the sub children
		ArrayList<CSTNode> children = node.getChildren();
		Iterator<CSTNode> iter = children.iterator();
		while (iter.hasNext()) {
			CSTNode child = iter.next();
			switch(child.getName()) {
			case "import_decl":
				ret.addIRImportDecl(parseIRImportDecl(child));
				break;
			case "field_decl":
				ret.addIRFieldDecls(parseIRFieldDecls(child));
				break;
			case "method_decl":
				ret.addMethodDecl(parseIRMethodDecl(child));
				break;
			default:
				System.out.println("node's name is wrong");
			}
		}
		return ret;
	}
	
	private static IRImportDecl parseIRImportDecl(CSTNode node) { 
		assert(node.getChildren().size() >= 2);
		return new IRImportDecl(node.getChildren().get(1).getToken());
	}
	
	private static IRMethodDecl parseIRMethodDecl(CSTNode node) {
		assert(node.getChildrenSize() >= 4);
		
		// Method declaration consists of (return)type, name, Variable, block 
		Token type = node.getChild(0).getChild(0).getToken();
		Token name = node.getChild(1).getToken();
		ArrayList<Variable> Variables = parseVariableList(node.getChild(2));
		IRBlock block = parseIRBlock(node.getChild(3));
		return new IRMethodDecl(type, name, Variables, block);
	}

	private static IRBlock parseIRBlock(CSTNode node) {
		//
		return null;
	}

	private static ArrayList<Variable> parseVariableList(CSTNode node) {
		ArrayList<Variable> ret = new ArrayList<Variable>();
		
		while (node.hasChild() && node.getChildrenSize()>= 3) {
			Token type = node.getChild(0).getChild(0).getToken();
			Token name = node.getChild(1).getToken();
			ret.add(new Variable(type, name));
			node = node.getChild(2);
		}
		
		return ret;
	}

	private static ArrayList<IRFieldDecl> parseIRFieldDecls(CSTNode node) {
		Token type = node.getChild(0).getChild(0).getToken();
		BasicTypeDesc basicType = BasicTypeDesc.GetInstance(type.getText());
		
		node = node.getChildren().get(1);
		ArrayList<IRFieldDecl> ret = new ArrayList<IRFieldDecl>();
		
		// Top-down 
		while (node.hasChild()) {
			Token name = node.getChild(0).getToken();
			CSTNode arrayNode = node.getChild(1);
			TypeDesc nowType = basicType;
			
			/// this type maybe array type
			if (arrayNode.hasChild()) {
				Token lengthToken = arrayNode.getChild(0).getToken();
				int length = Integer.parseInt(lengthToken.getText());
				nowType = new ArrayTypeDesc(nowType, length);
			}
			ret.add(new IRFieldDecl(nowType, name));
			node = node.getChild(2);
		}
		
		return ret;	
	}

}
