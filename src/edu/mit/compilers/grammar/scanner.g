header {
package edu.mit.compilers.grammar;
}

options
{
  mangleLiteralPrefix = "TK_";
  language = "Java";
}

{@SuppressWarnings("unchecked")}
class DecafScanner extends Lexer;
options
{
  k = 2;
}

tokens 
{
  "bool";
  "break";
  "import";
  "continue";
  "else";
  "false"; 
  "for";
  "while";
  "if";
  "int"; 
  "return";
  "len";
  "true";
  "void";
}

// Selectively turns on debug tracing mode.
// You can insert arbitrary Java code into your parser/lexer this way.
{
  /** Whether to display debug information. */
  private boolean trace = false;

  public void setTrace(boolean shouldTrace) {
    trace = shouldTrace;
  }
  @Override
  public void traceIn(String rname) throws CharStreamException {
    if (trace) {
      super.traceIn(rname);
    }
  }
  @Override
  public void traceOut(String rname) throws CharStreamException {
    if (trace) {
      super.traceOut(rname);
    }
  }
}

LCURLY options { paraphrase = "{"; } : "{";
RCURLY options { paraphrase = "}"; } : "}";

ID options { paraphrase = "an identifier"; } : 
  ALPHA (ALPHANUM)*;

// Note that here, the {} syntax allows you to literally command the lexer
// to skip mark this token as skipped, or to advance to the next line
// by directly adding Java commands.
WS_ : (' ' | '\n' {newline();} | '\t') {_ttype = Token.SKIP; };
SL_COMMENT : "//" (~'\n')* '\n' {_ttype = Token.SKIP; newline (); };
ML_COMMENT:
  "/*"
  (
  '\n' {newline();} 
  | (~('*' | '\n')))*
  "*/"
  {_ttype = Token.SKIP;};

MINUS :	"-";
PLUS : 	"+";
MUL : 	"*";
DIV : 	"/";
MOD : 	"%";	
GE :	">=";
GT : 	">";
LE :	"<=";
LT : 	"<";
EQ :	"==";
NE : 	"!=";
AND : 	"&&";
OR :	"||";
NOT : 	"!";

ASSIGN :	"=";
ASSIGN_PLUS : 	"+=";
ASSIGN_MINUS : 	"-=";
ASSIGN_MUL : 	"*=";
ASSIGN_DIV : 	"/=";
ASSIGN_MOD : 	"%=";
INC:		"++";
DEC:		"--";

LPAREN:       "(";
RPAREN:       ")";
LBRACK:       "[";
RBRACK:       "]";
SEMI:         ";";
COMMA:        ",";
COLON:        ":";
QUESTION:     "?";

CHAR : '\'' CHAR_LETTER '\'';
STRING : '"' (CHAR_LETTER)* '"';
INT : DIGITS | HEX;

protected
ESC :  '\\' ('n'|'"'|'t'|'\\'|'\''|'r');
protected
DIGIT_LETTER : ('0'..'9');
protected
HEX_LETTER : DIGIT_LETTER | ('A'..'F') | ('a'..'f');
protected
ALPHANUM : ALPHA | DIGIT_LETTER;
protected
ALPHA : ('a'..'z')|('A'..'Z')|'_';
protected
DIGITS : (DIGIT_LETTER)+;
protected
HEX : ("0x") (HEX_LETTER)+;  
protected 
CHAR_LETTER : (ESC|~('\'' | '\t' | '\n' | '\\' | '"'));
