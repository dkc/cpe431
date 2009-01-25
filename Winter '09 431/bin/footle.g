/*
   Lexer
*/
header
{
}
class FootleLexer extends Lexer;

options
{
   k = 2;
   charVocabulary='\u0000'..'\u007F';
}

tokens /* var return if else while function true false new */
{
   VAR	        =  "var";
   RETURN		=  "return";
   IF			=  "if";
   ELSE			=  "else";
   WHILE		=  "while";
   FUNCTION		=  "function";
   TRUE			=  "true";
   FALSE		=  "false";
   NEW			=  "new";
}

LBRACE   :  "{"   ;
RBRACE   :  "}"   ;
SEMI     :  ";"   ;
COMMA    :  ","   ;
LPAREN   :  "("   ;
RPAREN   :  ")"   ;
ASSIGN   :  "="   ;
DOT      :  "."   ;
NOT      :  "!"   ;

// binops
AND      :  "&&"  ;
OR       :  "||"  ;
EQ       :  "=="  ;
LT       :  "<"   ;
GT       :  ">"   ;
NE       :  "!="  ;
LTE      :  "<="  ;
GTE      :  ">="  ;
PLUS     :  "+"   ;
MINUS    :  "-"   ;
TIMES    :  "*"   ;
DIVIDE   :  "/"   ;


ID options {testLiterals=true;}
         :  ('a'..'z' | 'A'..'Z')('a'..'z' | 'A'..'Z' | '0'..'9' | '_' | '?')* ;
         
/* string handling with escape characters borrowed from Eric Mahurin on the OSDir mailing list
 * at http://osdir.com/ml/parsers.antlr-interest/2004-07/msg00197.html */
 
STRING 	 : '"'! ( '\\' . | ~('\\'|'"') )* '"'!
	;

/******/

/* integer/float handling borrowed from a tutorial on specifying syntactic predicates to look ahead in the lexer
 * at http://www.cs.bris.ac.uk/Teaching/Resources/General/antlr/doc/lexer.html */

protected
INT 	 :   '0' | ('1'..'9') ('0'..'9')*
    ;

protected
FLOAT	 :   ('0' | ('1'..'9') ('0'..'9')*) '.' ('0'..'9')*
    ;

FLOAT_OR_INT
    	 :   ( INT '.' )  => FLOAT { $setType(FLOAT); }
    	 |   INT                   { $setType(INT); }
    ;
    
/*******/

WS       :  (  ' '
            |  '\t'
            |  '\f'
               // handle newlines
            |  (  options {generateAmbigWarnings=false;}
               :  "\r\n"   // Evil DOS
               |  '\r'     // Macintosh
               |  '\n'     // Unix (the right way)
               )
               { newline(); }
            )+
            { $setType(Token.SKIP); }
         ;

COMMENT  :  "#"(~'\n')* '\n'
            { newline(); $setType(Token.SKIP); }
         ;

/*
   Parser
*/

class FootleParser extends Parser;

options
{
   buildAST=true;
   defaultErrorHandler=false;
}

tokens
{
   PROGRAM;
   TYPES;
   TYPE;
   DECLS;
   FUNCS;
   DECL;
   DECLLIST;
   PARAMS;
   RETTYPE;
   BLOCK;
   STMTS;
   INVOKE;
   ARGS;
   NEG;
   ARGLIST;
   FUNCTION_NAME;
   FUNCTION_BODY;
}

/* the full program is a series of statements ("stmt"s); we'll allow blank programs */

program
	:	s:stmtlist
		{ #program = #([PROGRAM,"PROGRAM"], #s); }
;

stmtlist
	:	(stmt)*
;

/* acceptable statements:
 * _expr_ ;
 * _id_ = _expr_ ;
 * _expr_ . _id_ = _expr_ ;
 * var _id_ = _expr_ ;
 * return _expr_ ;
 * if ( _expr_ ) { _stmt_* }
 * if ( _expr_ ) { _stmt_* } else { _stmt_* }
 * while ( _expr_ ) { _stmt_* }
 * function _id_ ( _paramlist_ ) { _stmt_* }
 */

stmt
	: 	(ID ASSIGN) => stmtassign
	| 	expr (SEMI! | (DOT! ID ASSIGN! expr SEMI!))
	| 	VAR^ ID ASSIGN! expr SEMI!
	| 	RETURN^ expr SEMI!
	| 	IF^ LPAREN! expr RPAREN! LBRACE! stmtlist RBRACE! (ELSE! LBRACE! stmtlist RBRACE!)?
	| 	WHILE^ LPAREN! expr RPAREN! LBRACE! stmtlist RBRACE!
	|! 	FUNCTION name:ID LPAREN! params:paramlist RPAREN! LBRACE! body:stmtlist RBRACE!
		{ #stmt = #(FUNCTION, #([FUNCTION_NAME, "FUNCTION_NAME"], #name), #params, #([FUNCTION_BODY, "FUNCTION_BODY"], #body)); }
;

stmtassign
	:	ID ASSIGN^ expr SEMI!
;
 
/* valid expressions:
 * _int_
 * _float_
 * true
 * false
 * _id_
 * _string_
 * ( _expr_ )
 * _id_ ( _arglist_ )
 * ( _expr_ ) ( _arglist_ )
 * _expr_ _binop_ _expr_
 * ! _expr_
 * _expr_ . _id_
 * _expr_ . _id_ ( _arglist_ )
 * new _id_ ( _arglist_ )
 */

expr
	: (exprnr DOT) => exprfield
	| (exprnr operator) => binexp
	| exprnr
;

exprfield
	: exprnr DOT ID (LPAREN! arglist RPAREN!)?
;

binexp
	: exprnr operator exprnr
;

exprnr
	: INT
	| FLOAT
	| TRUE
	| FALSE
	| (ID arglist) => application
	| ID
	| s:STRING
		{ #exprnr = #([STRING, "STRING"], #s); }
	| LPAREN! expr RPAREN! (LPAREN! arglist RPAREN!)?
	| NOT^ expr
	| NEW^ ID (LPAREN! arglist RPAREN!)
;

application
	:! 	id:ID a:arglist
		{ #application = #([INVOKE, "INVOKE"], #id, #([ARGLIST, "ARGLIST"], #a)); }
;

paramlist
	: 	(ID (COMMA ID)*)?
;

arglist
	:	(expr (COMMA expr)*)?
;

operator
	: AND | OR | EQ | LT | GT | NE | LTE | GTE | PLUS | MINUS | TIMES | DIVIDE
;

/* program
   : STRING (STRING)*
      { #program = #([ARGS,"ARGS"], #program); }
   |! { #program = #([ARGS,"ARGS"]); }
; */