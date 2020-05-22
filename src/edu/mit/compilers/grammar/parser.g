header {
package edu.mit.compilers.grammar;

import edu.mit.compilers.cst.*;

}

options
{
  mangleLiteralPrefix = "TK_";
  language = "Java";
}

class DecafParser extends Parser;
options
{
  importVocab = DecafScanner;
  k = 3;
  buildAST = true;
}

tokens 
{
  PROGRAM;
  IMPORT_DECL;
  FIELD_DECL;
  METHOD_DECL;
  BLOCK;
  TYPE;
  STATEMENT;
  ASSIGN_EXPR;
  ASSIGN_OP;
  INCREMENT;
  METHOD_CALL;
  METHOD_NAME;
  LOCATION;
  EXPR;
  IMPORT_ARG;
  BIN_OP;
  ARITH_OP;
  REL_OP;
  EQ_OP;
  COND_OP;
  LITERAL;
  BOOL_LITERAL;
}

// Java glue code that makes error reporting easier.
// You can insert arbitrary Java code into your parser/lexer this way.
{
  // Do our own reporting of errors so the parser can return a non-zero status
  // if any errors are detected.
  /** Reports if any errors were reported during parse. */
  private boolean error;

  @Override
  public void reportError (RecognitionException ex) {
    // Print the error via some kind of error reporting mechanism.
    System.out.println(ex.getMessage());
    error = true;
  }
  @Override
  public void reportError (String s) {
    // Print the error via some kind of error reporting mechanism.
    error = true;
  }
  public boolean getError () {
    return error;
  }

  // Selectively turns on debug mode.

  /** Whether to display debug information. */
  private boolean trace = false;

  private CST cstTree = CST.getInstance();
  private CSTNode root = cstTree.getRoot();
  private CSTNode currentNode = root;
  
  public void setTrace(boolean shouldTrace) {
    trace = shouldTrace;
  }
  
  public CST getCST() {
  	return cstTree;
  }
  
  @Override
  public void traceIn(String rname) throws TokenStreamException {
    currentNode.addChild(rname);
    currentNode = currentNode.getLastChild();
    if (trace) {
      super.traceIn(rname);
    }
  }
  @Override
  public void traceOut(String rname) throws TokenStreamException {
    currentNode = currentNode.getParent();
    if (trace) {
      super.traceOut(rname);
    }
  }
  
  @Override
  public void match(int t) throws MismatchedTokenException, TokenStreamException {
    currentNode.addChild(LT(1));
    super.match(t);
  }
}



program
	: (import_decl)* (field_decl)* (method_decl)* EOF;

import_decl 
	: TK_import ID SEMI!;

field_decl
	: type field_decl_id_list SEMI!;

field_decl_id_list
	: ID field_decl_id_array field_decl_id_list_more;

field_decl_id_list_more
	: COMMA! ID field_decl_id_array field_decl_id_list_more
	| 
	;

field_decl_id_array
	: LBRACK! INT RBRACK!
	|
	;

method_decl 
	: method_decl_type ID LPAREN! method_decl_args_list RPAREN! block;

method_decl_type
	: type
	| TK_void;

method_decl_args_list
	: type ID method_decl_args_list_more
	|
	;

method_decl_args_list_more
	: COMMA! type ID method_decl_args_list_more
	|
	;

block
	: LCURLY! (field_decl)* (statement)* RCURLY!;

type
	: TK_int
	| TK_bool
	;

statement
	: assign_stmt
	| method_call_stmt
	| if_stmt
	| for_stmt
	| while_stmt
	| return_stmt
	| break_stmt
	| continue_stmt
	;   

assign_stmt
	: location assign_expr SEMI!;

method_call_stmt
	: method_call SEMI!;

if_stmt
	: TK_if LPAREN! expr RPAREN! block (TK_else block)?;

for_stmt
	: TK_for LPAREN! ID OP_ASSIGN expr SEMI! expr SEMI! location (compound_assign_op expr | increment) RPAREN! block;

while_stmt
	: TK_while LPAREN! expr RPAREN! block;

return_stmt
	: TK_return (expr)? SEMI!;

break_stmt
	: TK_break SEMI!;

continue_stmt
	: TK_continue SEMI!;

assign_expr 
	: assign_op expr
	| increment
	;

assign_op
	: OP_ASSIGN
	| OP_ASSIGN_PLUS
	| OP_ASSIGN_MINUS
	;

compound_assign_op 
	: OP_ASSIGN_PLUS
	| OP_ASSIGN_MINUS
	;
	
increment
	: OP_INC
	| OP_DEC
	;

method_call
	: method_name LPAREN! (import_arg_list)? RPAREN! ;

method_name 
	: ID ;

location 
	: ID
	| ID LBRACK! expr RBRACK!
	;

expr
	: location expr2
	| method_call expr2
	| literal expr2
	| TK_len LPAREN! ID RPAREN! expr2
	| OP_MINUS	expr expr2
	| OP_NOT expr expr2
	| LPAREN! expr RPAREN! expr2
	;

expr2	
	: ( bin_op )	=> bin_op expr
	| ( QUESTION ) 	=> QUESTION! expr COLON! expr
	| 	
	;

import_arg_list 
	: import_arg import_arg_list_more;

import_arg_list_more
	: COMMA! import_arg import_arg_list_more
	|
	;

import_arg
	: expr 
	| STRING
	;

bin_op	
	: arith_op
	| rel_op
	| eq_op
	| cond_op
	;

arith_op
	: OP_PLUS
	| OP_MINUS
	| OP_MUL
	| OP_DIV
	| OP_MOD
	;

rel_op
	: OP_LT
	| OP_GT
	| OP_LE
	| OP_GE
	;

eq_op	
	: OP_EQ
	| OP_NE
	;

cond_op 
	: OP_AND
	| OP_OR
	;

literal	
	: INT
	| CHAR
	| bool_literal
	;

bool_literal
	: TK_true
	| TK_false
	;

