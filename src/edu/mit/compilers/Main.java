package edu.mit.compilers;

import antlr.Token;
import edu.mit.compilers.cst.CST;
import edu.mit.compilers.cst.CSTParser;
import edu.mit.compilers.grammar.DecafParser;
import edu.mit.compilers.grammar.DecafParserTokenTypes;
import edu.mit.compilers.grammar.DecafScanner;
import edu.mit.compilers.grammar.DecafScannerTokenTypes;
import edu.mit.compilers.ir.common.IR;
import edu.mit.compilers.lowercode.ThreeAddressCodeList;
import edu.mit.compilers.lowercode.convertor.LowerCodeConvertor;
import edu.mit.compilers.lowercode.convertor.SymbolTable;
import edu.mit.compilers.semantic.Renamer;
import edu.mit.compilers.semantic.checker.SemanticChecker;
import edu.mit.compilers.semantic.checker.SemanticError;
import edu.mit.compilers.tools.CLI;
import edu.mit.compilers.tools.CLI.Action;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;


class Main {
	public static void main(String[] args) {
		try {
			CLI.parse(args, new String[0]);
			InputStream inputStream = args.length == 0 ? System.in : new java.io.FileInputStream(CLI.infile);
			PrintStream outputStream = CLI.outfile == null ? System.out
					: new java.io.PrintStream(new java.io.FileOutputStream(CLI.outfile));
			// CLI.target = Action.SCAN;
			if (CLI.target == Action.SCAN) {
				DecafScanner scanner = new DecafScanner(new DataInputStream(inputStream));
				scanner.setTrace(CLI.debug);
				Token token;
				boolean done = false;
				while (!done) {
					try {
						for (token = scanner.nextToken(); token.getType() != DecafParserTokenTypes.EOF; token = scanner
								.nextToken()) {
							String type;
							String text = token.getText();
							switch (token.getType()) {
							// TODO: add strings for the other types here...
							case DecafScannerTokenTypes.ID:
								type = " IDENTIFIER";
								break;
							case DecafScannerTokenTypes.CHAR:
								type = " CHARLITERAL";
								break;
							case DecafScannerTokenTypes.INT:
								type = " INTLITERAL";
								break;
							case DecafScannerTokenTypes.STRING:
								type = " STRINGLITERAL";
								break;
							case DecafScannerTokenTypes.TK_true:
							case DecafScannerTokenTypes.TK_false:
								type = " BOOLEANLITERAL";
								break;
							default:
								// System.out.println(token.getType());
								type = "";
								break;
							}
							outputStream.println(token.getLine() + type + " " + text);
						}
						done = true;
					} catch (Exception e) {
						// print the error:
						System.err.println(CLI.infile + " " + e);
						if (e.getMessage().contains("0xA")) {
							scanner.consume();
							scanner.newline();
						} else {
							scanner.consume();
						}
						// scanner.consume();
					}
				}
			} else if (CLI.target == Action.PARSE) {
				DecafScanner scanner = new DecafScanner(new DataInputStream(inputStream));
				DecafParser parser = new DecafParser(scanner);
				parser.setTrace(CLI.debug);
				parser.program();
				if (parser.getError()) {
					System.exit(1);
				}
			} else if (CLI.target == Action.INTER || CLI.target == Action.ASSEMBLY || CLI.target == Action.DEFAULT) {
				DecafScanner scanner = new DecafScanner(new DataInputStream(inputStream));
				DecafParser parser = new DecafParser(scanner);
				parser.setTrace(CLI.debug);
				parser.program();
				if (parser.getError()) {
					System.exit(1);
				}

				CST tree = parser.getCST();

				// After pruned
				tree.PrunTree();
				if (CLI.debug) {
					System.out.println(tree.show());
				}

				// Get IR-tree
				IR ir = CSTParser.parseIRProgram(tree);
				if (CLI.debug){
					System.out.println(ir.show());
				}

				// Semantic check
				SemanticProcess(ir, CLI.debug);
				if (CLI.target == Action.INTER) {
					return;
				}

				// Rename
				Renamer renamer = new Renamer();
				ir = renamer.Rename(ir.clone());
				System.out.println(ir.show());

				// Assembly
				AssemblyProcess(ir, CLI.debug, false);
			}
		} catch (Exception e) {
			// print the error:
			System.err.println(CLI.infile + " " + e);
			e.printStackTrace(System.err);
		}
	}

	private static void SemanticProcess(IR tree, boolean debug) throws CloneNotSupportedException {
		SemanticChecker check = new SemanticChecker();
		check.init("test.sh");
		ArrayList<SemanticError> errors = check.check(tree);

		if (debug) {
			System.out.println(tree.show());
		}

		if (!errors.isEmpty()) {
			check.reportErrors();
			System.exit(1);
		}
	}

	private static void AssemblyProcess(IR tree, boolean debug, boolean optimized) throws CloneNotSupportedException {
		// generate three address code
		LowerCodeConvertor convertor = new LowerCodeConvertor();
		convertor.ConvertToLowCode(tree);
		ThreeAddressCodeList codes = convertor.getResult();
		SymbolTable symbolTable = convertor.getSymbolTable();

		if (debug) {
			System.out.println(symbolTable.getNamesOfSymbol());
			System.out.println(codes.show());
		}
		// generate assembly code

	}
}
