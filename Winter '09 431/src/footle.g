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
DIVIDE	 :  "/"   ;


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
   INVOKE;
   ARGUMENTS;
   FUNCTION_NAME;
   FUNCTION_BODY;
   FIELD_LOOKUP;
}

/* the full program is a series of statements ("stmt"s); we'll allow blank programs */

program
	:	s:stmtlist
		{ #program = #([PROGRAM,"Program"], #s); }
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
	{System.out.println("stmt");}
	: 	(identifier ASSIGN) => stmtassign
		{	System.out.println("stmt:assignment");
		}
	| 	expr (SEMI! | (DOT! identifier ASSIGN! expr SEMI!))
		{	System.out.println("stmt:expression");
		}
	| 	VAR^ identifier ASSIGN! expr SEMI!
		{	System.out.println("stmt:var dec");
		}
	| 	RETURN^ expr SEMI!
		{	System.out.println("stmt:return");
		}
	| 	IF^ LPAREN! expr RPAREN! LBRACE! stmtlist RBRACE! (ELSE! LBRACE! stmtlist RBRACE!)?
		{	System.out.println("stmt:if");
		}
	| 	WHILE^ LPAREN! expr RPAREN! LBRACE! stmtlist RBRACE!
		{	System.out.println("stmt:while");
		}
	|! 	FUNCTION name:identifier params:paramlist LBRACE! body:stmtlist RBRACE!
		{	System.out.println("stmt:function dec");
			#stmt = #(FUNCTION, #([FUNCTION_NAME, "Function Name"], #name), #params, #([FUNCTION_BODY, "Function Body"], #body));
		}
;

stmtassign
	:	identifier ASSIGN^ expr SEMI!
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

/* this rule encompasses both straight field lookups and method calls--"Arguments" will be empty on a field lookup */
exprfield
	:! 	exp:exprnr DOT id:identifier (a:arglist)?
		{ #exprfield = #([FIELD_LOOKUP, "Field Lookup"], #exp, #id, #([ARGUMENTS, "Arguments"], #a)); }
;

/* a list of non-LL recursive expressions separated from expr above to keep it from freaking out--thanks, LL parser */
exprnr
	: INT
	| FLOAT
	| TRUE
	| FALSE
	| (identifier arglist) => application
	| identifier
	| s:STRING
		{ #exprnr = #([STRING, "String"], #s); }
	| LPAREN! expr RPAREN! (arglist)?
	| NOT^ expr
	| NEW^ identifier arglist
;

/* any function application falls under this rule, including built-ins */
application
	:! 	id:identifier args:arglist
		{ #application = #([INVOKE, "Invoke"], #id, #([ARGUMENTS, "Arguments"], #args)); }
;

/* list of parameters in a function definition */
paramlist
	: 	LPAREN! (identifier (COMMA! identifier)*)? RPAREN!
;

/* list of arguments in an application */
arglist
	:	LPAREN! (expr (COMMA! expr)*)? RPAREN!
;

/* all binary expressions are Here */

binexp
	:	binexplvl8
		{System.out.println("binexp");}
;

binexplvl8
	:!	(binexplvl7 l8op binexplvl7 l8op) => exp1:binexplvl7 op1:l8op exp2:binexplvl7 op2:l8op exp3:binexplvl8
		{	#binexplvl8 = #(op2, #(op1, exp1, exp2), exp3); }
	|!	(binexplvl7 l8op) => exp4:binexplvl7 op3:l8op exp5:binexplvl8
		{	#binexplvl8 = #(op3, exp4, exp5); }
	|	binexplvl7
;

l8op
	:	EQ
;

binexplvl7
	:!	(binexplvl6 l7op binexplvl6 l7op) => exp1:binexplvl6 op1:l7op exp2:binexplvl6 op2:l7op exp3:binexplvl7
		{	#binexplvl7 = #(op2, #(op1, exp1, exp2), exp3); }
	|!	(binexplvl6 l7op) => exp4:binexplvl6 op3:l7op exp5:binexplvl7
		{	#binexplvl7 = #(op3, exp4, exp5); }
	|	binexplvl6
;

l7op
	:	AND | OR
;

binexplvl6
	:!	(binexplvl5 l6op binexplvl5 l6op) => exp1:binexplvl5 op1:l6op exp2:binexplvl5 op2:l6op exp3:binexplvl6
		{	#binexplvl6 = #(op2, #(op1, exp1, exp2), exp3); }
	|!	(binexplvl5 l6op) => exp4:binexplvl5 op3:l6op exp5:binexplvl6
		{	#binexplvl6 = #(op3, exp4, exp5); }
	|	binexplvl5
;

l6op
	:	GT | GTE | LT | LTE
;

binexplvl5
	:!	(binexplvl4 l5op binexplvl4 l5op ) => exp1:binexplvl4 op1:l5op exp2:binexplvl4 op2:l5op exp3:binexplvl5
		{	#binexplvl5 = #(op2, #(op1, exp1, exp2), exp3); }
	|!	(binexplvl4 l5op) => exp4:binexplvl4 op3:l5op exp5:binexplvl5
		{	#binexplvl5 = #(op3, exp4, exp5); }
	|	binexplvl4
;

l5op
	:	DIVIDE
;

binexplvl4
	:!	(binexplvl3 l4op binexplvl3 l4op) => exp1:binexplvl3 op1:l4op exp2:binexplvl3 op2:l4op exp3:binexplvl4
		{	#binexplvl4 = #(op2, #(op1, exp1, exp2), exp3); }
	|!	(binexplvl3 l4op) => exp4:binexplvl3 op3:l4op exp5:binexplvl4
		{	#binexplvl4 = #(op3, exp4, exp5); }
	|	binexplvl3
;

l4op
	:	PLUS | MINUS
;

binexplvl3
	:!	(exprnr l3op exprnr l3op ) => exp1:exprnr op1:l3op exp2:exprnr op2:l3op exp3:binexplvl3
		{	#binexplvl3 = #(op2, #(op1, exp1, exp2), exp3); }
	|!	(exprnr l3op) => exp4:exprnr op3:l3op exp5:binexplvl3
		{	#binexplvl3 = #(op3, exp4, exp5); }
	|	exprnr
;

l3op
	:	TIMES
;

operator
	: 	AND | OR | EQ | LT | GT | NE | LTE | GTE | PLUS | MINUS | TIMES | DIVIDE
;

identifier
	: 	id:ID
		{ #identifier = #([ID,"Identifier"], id); }
;