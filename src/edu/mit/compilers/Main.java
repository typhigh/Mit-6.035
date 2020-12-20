package edu.mit.compilers;

import antlr.Token;
import edu.mit.compilers.cst.CST;
import edu.mit.compilers.grammar.DecafParser;
import edu.mit.compilers.grammar.DecafParserTokenTypes;
import edu.mit.compilers.grammar.DecafScanner;
import edu.mit.compilers.grammar.DecafScannerTokenTypes;
import edu.mit.compilers.tools.CLI;
import edu.mit.compilers.tools.CLI.Action;
import edu.mit.compilers.utils.MainController;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.PrintStream;


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

				MainController controller = new MainController(CLI.debug);
				controller.setCstTree(tree);

				// get IR
				MainController.State state = controller.nextStep();
				assert state == MainController.State.INTER;

				// check semantic
				state = controller.nextStep();
				assert state == MainController.State.SEMANTIC_CHECKED;

				if (CLI.target == Action.ASSEMBLY || CLI.target == Action.DEFAULT) {
					// remane
					state = controller.nextStep();
					assert state == MainController.State.RENAMED;

					state = controller.nextStep();
					assert state == MainController.State.LOWERCODE_GENED;

					state = controller.nextStep();
					assert state == MainController.State.OPTIMIZED;

					state = controller.nextStep();
					assert state == MainController.State.ASSEMBLYCODE_GENED;
					// TODO:
					return;
				}
			}
		} catch (Exception e) {
			// print the error:
			System.err.println(CLI.infile + " " + e);
			e.printStackTrace(System.err);
		}
	}
}
