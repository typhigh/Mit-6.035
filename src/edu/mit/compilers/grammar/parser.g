header {
package edu.mit.compilers.grammar;
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
  COMPOUND_ASSIGN_OP;
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

  public void setTrace(boolean shouldTrace) {
    trace = shouldTrace;
  }
  @Override
  public void traceIn(String rname) throws TokenStreamException {
    if (trace) {
      super.traceIn(rname);
    }
  }
  @Override
  public void traceOut(String rname) throws TokenStreamException {
    if (trace) {
      super.traceOut(rname);
    }
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
	: location assign_expr SEMI!
	| method_call SEMI!
	| TK_if LPAREN! expr RPAREN! block (TK_else block)?
	| TK_for LPAREN! ID ASSIGN expr SEMI! expr SEMI! location (compound_assign_op expr | increment) RPAREN! block
	| TK_while LPAREN! expr RPAREN! block
	| TK_return (expr)? SEMI!
	| TK_break SEMI!
	| TK_continue SEMI!
	;   

assign_expr 
	: assign_op expr
	| increment
	;

assign_op
	: ASSIGN
	| compound_assign_op
	;

compound_assign_op
	: ASSIGN_PLUS
	| ASSIGN_MINUS
	;

increment
	: INC
	| DEC
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
	| MINUS	expr expr2
	| NOT expr expr2
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
	: PLUS
	| MINUS
	| MUL
	| DIV
	| MOD
	;

rel_op
	: LT
	| GT
	| LE
	| GE
	;

eq_op	
	: EQ
	| NE
	;

cond_op 
	: AND
	| OR
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

