package edu.mit.compilers.cst;

import java.util.ArrayList;
import java.util.Iterator;

import antlr.Token;
import edu.mit.compilers.ir.IRProgram;
import edu.mit.compilers.ir.decl.IRFieldDecl;
import edu.mit.compilers.ir.decl.IRImportDecl;
import edu.mit.compilers.ir.decl.IRMethodDecl;
import edu.mit.compilers.ir.statement.IRBlock;
import edu.mit.compilers.semantic.BasicTypeDesc;
import edu.mit.compilers.semantic.Variable;

public class CSTParser {

	public static IRProgram parseIRProgram(CSTNode node) {
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
				ret.addIRFieldDecl(parseIRFieldDecl(child));
				break;
			case "method_decl":
				ret.addMethodDecls(parseIRMethodDecl(child));
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
		ArrayList<CSTNode> children = node.getChildren();
		assert(children.size() >= 4);
		
		// Method declaration consists of (return)type, name, Variable, block 
		Token type = children.get(0).getChildren().get(0).getToken();
		Token name = children.get(1).getToken();
		ArrayList<Variable> Variables = parseVariableList(children.get(2));
		IRBlock block = parseIRBlock(children.get(3));
		return new IRMethodDecl(type, name, Variables, block);
	}

	private static IRBlock parseIRBlock(CSTNode node) {
		//
		return null;
	}

	private static ArrayList<Variable> parseVariableList(CSTNode node) {
		ArrayList<Variable> ret = new ArrayList<Variable>();
		
		while (node.getChildren() != null && node.getChildren().size() >= 3) {
			ArrayList<CSTNode> children = node.getChildren();
			Token type = children.get(0).getToken();
			Token name = children.get(1).getToken();
			ret.add(new Variable(type, name));
			node = children.get(3);
		}
		
		return ret;
	}

	private static IRFieldDecl parseIRFieldDecl(CSTNode node) {
		return null;
	}
}
