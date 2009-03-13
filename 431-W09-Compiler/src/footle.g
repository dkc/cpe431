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

/*******************************************************************************
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
   ARGUMENTS;
   BINOP;
   CONST_INT;
   CONST_FLOAT;
   CONST_BOOLEAN;
   CONST_IDENTIFIER;
   CONST_STRING;
   ELSEF;
   FIELD_LOOKUP;
   FUNCTION_DEC;
   FUNCTION_NAME;
   FUNCTION_BODY;
   FUNCTION_COLLECTION;
   IFF;
   INVOKE;
   METHOD_CALL;
   PROGRAM;
   THENF;
}

{
	private int functionCounter = 0;
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
	: 	(identifier ASSIGN) => stmtassign
	| 	expr (SEMI! | (ASSIGN^ expr SEMI!))
	| 	VAR^ identifier ASSIGN! expr SEMI!
	| 	RETURN^ expr SEMI!
	|! 	IF LPAREN! exp1:expr RPAREN! LBRACE! s1:stmtlist RBRACE! (ELSE! LBRACE! s2:stmtlist RBRACE!)?
		{ #stmt = #([IFF, "IFF"], #exp1, #([THENF, "THENF"], s1), #([ELSEF, "ELSEF"], s2)); }  
	| 	WHILE^ LPAREN! expr RPAREN! LBRACE! stmtlist RBRACE!
	|!	f:functions
		{ #stmt = #([FUNCTION_COLLECTION, "FUNCTION_COLLECTION"], #f); }
;

stmtassign
	:	identifier ASSIGN^ expr SEMI!
;
 
functions
	:	(function FUNCTION) => function functions
	|	function
;
function
	:! 	FUNCTION name:identifier params:paramlist LBRACE! body:stmtlist RBRACE!
		{
			#function = #([FUNCTION_DEC, "FUNCTION_DEC"], #([FUNCTION_NAME, "FUNCTION_NAME"], #name), #params, #([FUNCTION_BODY, "FUNCTION_BODY"], #body));
			functionCounter++;
		}
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
	:	(exprnr DOT identifier arglist) => exprmethodcall
	|! 	exp:exprnr DOT id:identifier
		{ #exprfield = #([FIELD_LOOKUP, "FIELD_LOOKUP"], #exp, #id); }
;
/* working on handling nested method calls */
exprmethodcall
	:!	(exprnr DOT identifier arglist DOT) => expm:loneexprmethodcall DOT id1:identifier a1:arglist
		{ #exprmethodcall  = #([METHOD_CALL, "METHOD_CALL"], #expm, #id1, #([ARGUMENTS, "ARGUMENTS"], #a1)); }
	|	loneexprmethodcall
;
loneexprmethodcall
	:!	exp:exprnr DOT id:identifier a:arglist
		{ #loneexprmethodcall  = #([METHOD_CALL, "METHOD_CALL"], #exp, #id, #([ARGUMENTS, "ARGUMENTS"], #a)); }
;

/* a list of non-LL recursive expressions separated from expr above to keep it from freaking out--thanks, LL parser */
exprnr
	:!	i:INT
		{ #exprnr = #([CONST_INT, "CONST_INT"], i); }
	|!	f:FLOAT
		{ #exprnr = #([CONST_FLOAT, "CONST_FLOAT"], f); }
	|!	b1:TRUE
		{ #exprnr = #([CONST_BOOLEAN, "CONST_BOOLEAN"], b1); }
	|!	b2:FALSE
		{ #exprnr = #([CONST_BOOLEAN, "CONST_BOOLEAN"], b2); }
	|	(identifier arglist) => application
	|	id:identifier
	|!	s:STRING
		{ #exprnr = #([CONST_STRING, "CONST_STRING"], s); }
	|	LPAREN! expr RPAREN! (arglist)?
	|	NOT^ expr
	|!	n:NEW id2:identifier args:arglist
		{ #exprnr = #(n, id2, #([ARGUMENTS, "ARGUMENTS"], args)); }
;
/* any function application falls under this rule, including built-ins */
application
	:! 	id:identifier args:arglist
		{ #application = #([INVOKE, "INVOKE"], #id, #([ARGUMENTS, "ARGUMENTS"], #args)); }
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
;

binexplvl8
	:!	(binexplvl7 l8op binexplvl7 l8op) => exp1:binexplvl7 op1:l8op exp2:binexplvl7 op2:l8op exp3:binexplvl8
		{	#binexplvl8 = #([BINOP, "BINOP"], op2, #([BINOP, "BINOP"], op1, exp1, exp2), exp3); }
	|!	(binexplvl7 l8op) => exp4:binexplvl7 op3:l8op exp5:binexplvl8
		{	#binexplvl8 = #([BINOP, "BINOP"], op3, exp4, exp5); }
	|	binexplvl7
;
l8op
	:	op:EQ
;

binexplvl7
	:!	(binexplvl6 l7op binexplvl6 l7op) => exp1:binexplvl6 op1:l7op exp2:binexplvl6 op2:l7op exp3:binexplvl7
		{	#binexplvl7 = #([BINOP, "BINOP"], op2, #([BINOP, "BINOP"], op1, exp1, exp2), exp3); }
	|!	(binexplvl6 l7op) => exp4:binexplvl6 op3:l7op exp5:binexplvl7
		{	#binexplvl7 = #([BINOP, "BINOP"], op3, exp4, exp5); }
	|	binexplvl6
;
l7op
	:	AND | OR
;

binexplvl6
	:!	(binexplvl5 l6op binexplvl5 l6op) => exp1:binexplvl5 op1:l6op exp2:binexplvl5 op2:l6op exp3:binexplvl6
		{	#binexplvl6 = #([BINOP, "BINOP"], op2, #([BINOP, "BINOP"], op1, exp1, exp2), exp3); }
	|!	(binexplvl5 l6op) => exp4:binexplvl5 op3:l6op exp5:binexplvl6
		{	#binexplvl6 = #([BINOP, "BINOP"], op3, exp4, exp5); }
	|	binexplvl5
;
l6op
	:	GT | GTE | LT | LTE
;

binexplvl5
	:!	(binexplvl4 l5op binexplvl4 l5op ) => exp1:binexplvl4 op1:l5op exp2:binexplvl4 op2:l5op exp3:binexplvl5
		{	#binexplvl5 = #([BINOP, "BINOP"], op2, #([BINOP, "BINOP"], op1, exp1, exp2), exp3); }
	|!	(binexplvl4 l5op) => exp4:binexplvl4 op3:l5op exp5:binexplvl5
		{	#binexplvl5 = #([BINOP, "BINOP"], op3, exp4, exp5); }
	|	binexplvl4
;
l5op
	:	DIVIDE
;

binexplvl4
	:!	(binexplvl3 l4op binexplvl3 l4op) => exp1:binexplvl3 op1:l4op exp2:binexplvl3 op2:l4op exp3:binexplvl4
		{	#binexplvl4 = #([BINOP, "BINOP"], op2, #([BINOP, "BINOP"], op1, exp1, exp2), exp3); }
	|!	(binexplvl3 l4op) => exp4:binexplvl3 op3:l4op exp5:binexplvl4
		{	#binexplvl4 = #([BINOP, "BINOP"], op3, exp4, exp5); }
	|	binexplvl3
;
l4op
	:	PLUS | MINUS
;

binexplvl3
	:!	(exprnr l3op exprnr l3op ) => exp1:exprnr op1:l3op exp2:exprnr op2:l3op exp3:binexplvl3
		{	#binexplvl3 = #([BINOP, "BINOP"], op2, #([BINOP, "BINOP"], op1, exp1, exp2), exp3); }
	|!	(exprnr l3op) => exp4:exprnr op3:l3op exp5:binexplvl3
		{	#binexplvl3 = #([BINOP, "BINOP"], op3, exp4, exp5); }
	|	(exprnr DOT) => exprfield
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
		{ #identifier = #([CONST_IDENTIFIER,"CONST_IDENTIFIER"], id); }
;

/*
 *	Tree Parser
 	- suspecting that ids are handled differently in certain places in the parser--need to run test cases again
 */

{
import java.util.ArrayList;

import Expressions.*;
import Expressions.Const.*;
import Environment.*;
import Values.*;
}

class FootleTreeParser extends TreeParser;

{
	private int nextUniqueRegisterId = 0; /* should ALWAYS be the NEXT UNIQUE register # */
	private int closureCounter = 0; /* ditto previous int; always increment after use */
	
	private void error (String errormsg)
	{
		System.out.println(errormsg);
		System.exit(1);
	}
}

validate returns [Sequence finalStmtResult = null]
    : 	#(PROGRAM finalStmtResult=sequence)
;

sequence returns [Sequence compiledSequence = null]
{
	ArrayList<CodeAndReg> seq = new ArrayList<CodeAndReg>();
	CodeAndReg fSR = null;
}
	:	(fSR=stmt { seq.add(fSR); })*
	{	compiledSequence = new Sequence(seq, nextUniqueRegisterId++);
	}
;

/* statements:
 x _expr_ ;
 x _id_ = _expr_ ;
 x _expr_ . _id_ = _expr_ ;
 x var _id_ = _expr_ ;
 x return _expr_ ;
 x if ( _expr_ ) { _stmt_* } -- pretty sure all if/thens have an empty else slapped on by the parser if it's not present, so we only need one rule
 x if ( _expr_ ) { _stmt_* } else { _stmt_* }
 x while ( _expr_ ) { _stmt_* }
 x function _id_ ( _paramlist_ ) { _stmt_* } -- grouped into FUNCTION_COLLECTIONs
 */
stmt returns [CodeAndReg stmtResult = null]
{
	CodeAndReg exprResult, stmtListResult, thenResult, elseResult, assignResult;
	FuncDec funcDec;
	ArrayList<FuncDec> funcDecList = new ArrayList<FuncDec>();
}
	:	stmtResult=expr
	|	#(IFF exprResult=expr #(THENF thenResult=sequence) #(ELSEF elseResult=sequence))
		{	stmtResult = new IfExp(exprResult, thenResult, elseResult, nextUniqueRegisterId++);
		}
	|	(#(ASSIGN #(CONST_IDENTIFIER ID) expr)) => #(ASSIGN #(CONST_IDENTIFIER id:ID) exprResult=expr)
		{	/* binding to variables; VarMuts should always work if the static pass determines use before initialization */
			stmtResult = new VarMut(id.toString(), exprResult, nextUniqueRegisterId++);
		}
	|	#(ASSIGN #(FIELD_LOOKUP exprResult=expr #(CONST_IDENTIFIER fieldId:ID)) assignResult=expr) 
		{	stmtResult = new FieldMut(exprResult, fieldId.toString(), assignResult, nextUniqueRegisterId++);
		}
	|	#(WHILE exprResult=expr stmtListResult=sequence)
		{	stmtResult = new WhileExp(exprResult, stmtListResult, nextUniqueRegisterId++);
		}
	|	#(VAR #(CONST_IDENTIFIER id2:ID) exprResult=expr)
		{	stmtResult = new Bind(id2.toString(), exprResult, nextUniqueRegisterId++);
		}
	|	#(RETURN exprResult=expr)
		{	stmtResult = new FReturn(exprResult, nextUniqueRegisterId++);
		}
	|	#(FUNCTION_COLLECTION (funcDec=function {funcDecList.add(funcDec);} )+)
		{	stmtResult = new FuncBind(funcDecList, nextUniqueRegisterId++);
		}
;


/* field mutations like c.abc = 5; are broken? curious */

function returns [FuncDec funcDecReturn = null]
{
	Sequence functionBody;
	ArrayList<String> params;
}
	:	#(FUNCTION_DEC #(FUNCTION_NAME #(CONST_IDENTIFIER name:ID)) params=paramlist #(FUNCTION_BODY functionBody=sequence))
		{	funcDecReturn = new FuncDec(name.toString(), params, functionBody, nextUniqueRegisterId++);
		}
;

paramlist returns [ArrayList<String> parameters = new ArrayList<String>()]
{
	String paramName;
}
	:	(
			paramName=param_id {parameters.add(paramName);}
		)*
;

param_id returns [String paramName = null]
	:	#(CONST_IDENTIFIER (name:ID { paramName = name.toString(); } ))
;

/* expressions:
 x _int_
 x _float_
 x true
 x false
 x _id_
 x _string_
 | ( _expr_ ) -- parser stripped parentheses
 x _id_ ( _arglist_ ) -- application
 ? ( _expr_ ) ( _arglist_ )
 x _expr_ _binop_ _expr_
 x ! _expr_
 x _expr_ . _id_
 x _expr_ . _id_ ( _arglist_ ) -- "args" isn't implemented yet, though
 x new _id_ ( _arglist_ )
 */
expr returns [CodeAndReg result = null]
{
	CodeAndReg expression;
	ArrayList<CodeAndReg> argumentList;
}
	:	result=binop
	|	result=const_val
	|	#(NOT expression=expr)
		{	result = new UnaryOperation("not", expression, nextUniqueRegisterId++);
		}
	|	#(FIELD_LOOKUP expression=expr #(CONST_IDENTIFIER fieldId:ID))
		{	result = new FieldLookup(expression, fieldId.toString(), nextUniqueRegisterId++);
		}
	|	#(METHOD_CALL expression=expr #(CONST_IDENTIFIER methodId:ID) argumentList=args)
		{	result = new MethodCall(new FieldLookup(expression, methodId.toString(), nextUniqueRegisterId++), argumentList, nextUniqueRegisterId++);
		} 
	|	#(INVOKE #(CONST_IDENTIFIER functionName:ID) argumentList=args)
		{	String funName = functionName.toString();
			result = new Application(new VarRef(funName, nextUniqueRegisterId++), argumentList, nextUniqueRegisterId++);
			if (funName.equals("stringLength")) {
				result = new UnaryOperation(funName, argumentList.get(0), nextUniqueRegisterId++);
			} else if (funName.equals("not")) {
				result = new UnaryOperation(funName, argumentList.get(0), nextUniqueRegisterId++);
			} else if (funName.equals("int?")) {
				result = new UnaryOperation(funName, argumentList.get(0), nextUniqueRegisterId++);
			} else if (funName.equals("string?")) {
				result = new UnaryOperation(funName, argumentList.get(0), nextUniqueRegisterId++);
			} else if (funName.equals("bool?")) {
				result = new UnaryOperation(funName, argumentList.get(0), nextUniqueRegisterId++);
			} else if (funName.equals("float?")) {
				result = new UnaryOperation(funName, argumentList.get(0), nextUniqueRegisterId++);
			} else if (funName.equals("void?")) {
				result = new UnaryOperation(funName, argumentList.get(0), nextUniqueRegisterId++);
			} else if (funName.equals("closure?")) {
				result = new UnaryOperation(funName, argumentList.get(0), nextUniqueRegisterId++);
			} else if (funName.equals("plain?")) {
				result = new UnaryOperation(funName, argumentList.get(0), nextUniqueRegisterId++);
			} else if (funName.equals("print")) {
				result = new UnaryOperation(funName, argumentList.get(0), nextUniqueRegisterId++);
			} else if (funName.equals("subString")) {
				result = new PrimitiveOperation(funName, argumentList, nextUniqueRegisterId++);
			} else if (funName.equals("string=?")) {
				result = new PrimitiveOperation(funName, argumentList, nextUniqueRegisterId++);
			} else if (funName.equals("stringLessThan?")) {
				result = new PrimitiveOperation(funName, argumentList, nextUniqueRegisterId++);
			} else if (funName.equals("instanceof")) {
				result = new PrimitiveOperation(funName, argumentList, nextUniqueRegisterId++);
			} else if (funName.equals("readLine")) {
				result = new PrimitiveOperation(funName, argumentList, nextUniqueRegisterId++);
			}
		}
	|	#(NEW #(CONST_IDENTIFIER objectName:ID) argumentList=args)
		{	result = new NewObj(new VarRef(objectName.toString(), nextUniqueRegisterId++), argumentList, nextUniqueRegisterId++);
		}
;


binop returns [CodeAndReg resultRegister = null]
{
	CodeAndReg lhs, rhs;
}
	:	(#(BINOP AND expr expr)) => #(BINOP AND lhs=expr rhs=expr)
		{ 	resultRegister = new Binop(lhs, "&&", rhs, nextUniqueRegisterId++);
		}
	|	(#(BINOP OR expr expr)) => #(BINOP OR lhs=expr rhs=expr)
		{ 	resultRegister = new Binop(lhs, "||", rhs, nextUniqueRegisterId++);
		}
	|	(#(BINOP EQ expr expr)) => #(BINOP EQ lhs=expr rhs=expr)
		{	resultRegister = new Binop(lhs, "==", rhs, nextUniqueRegisterId++);
		}
	|	(#(BINOP LT expr expr)) => #(BINOP LT lhs=expr rhs=expr)
		{ 	resultRegister = new Binop(lhs, "<", rhs, nextUniqueRegisterId++);
		}
	|	(#(BINOP GT expr expr)) => #(BINOP GT lhs=expr rhs=expr)
		{ 	resultRegister = new Binop(lhs, ">", rhs, nextUniqueRegisterId++);
		}
	|	(#(BINOP NE expr expr)) => #(BINOP NE lhs=expr rhs=expr)
		{ 	resultRegister = new Binop(lhs, "!=", rhs, nextUniqueRegisterId++);
		}
	|	(#(BINOP LTE expr expr)) => #(BINOP LTE lhs=expr rhs=expr)
		{ 	resultRegister = new Binop(lhs, "<=", rhs, nextUniqueRegisterId++);
		}
	|	(#(BINOP GTE expr expr)) => #(BINOP GTE lhs=expr rhs=expr)
		{ 	resultRegister = new Binop(lhs, ">=", rhs, nextUniqueRegisterId++);
		}
	|	(#(BINOP PLUS expr expr)) => #(BINOP PLUS lhs=expr rhs=expr)
		{ 	resultRegister = new Binop(lhs, "+", rhs, nextUniqueRegisterId++);
		}
	|	(#(BINOP MINUS expr expr)) => #(BINOP MINUS lhs=expr rhs=expr)
		{ 	resultRegister = new Binop(lhs, "-", rhs, nextUniqueRegisterId++);
		}
	|	(#(BINOP TIMES expr expr)) => #(BINOP TIMES lhs=expr rhs=expr)
		{ 	resultRegister = new Binop(lhs, "*", rhs, nextUniqueRegisterId++);
		}
	|	(#(BINOP DIVIDE expr expr)) => #(BINOP DIVIDE lhs=expr rhs=expr)
		{ 	resultRegister = new Binop(lhs, "/", rhs, nextUniqueRegisterId++);
		}
;

const_val returns [CodeAndReg constValue = null]
	:	#(CONST_INT i:INT)
		{	constValue = new FInteger(new Integer(Integer.parseInt(i.toString())), nextUniqueRegisterId++);
		}
	|	#(CONST_FLOAT f:FLOAT)
		{	constValue = new FFloat(new Float(Float.parseFloat(f.toString())), nextUniqueRegisterId++);
		}
	|	(#(CONST_BOOLEAN TRUE)) => #(CONST_BOOLEAN TRUE)
		{	constValue = new FBoolean(new Boolean(true), nextUniqueRegisterId++);
		}
	|	(#(CONST_BOOLEAN FALSE)) => #(CONST_BOOLEAN FALSE)
		{	constValue = new FBoolean(new Boolean(false), nextUniqueRegisterId++);
		}
	|	#(CONST_IDENTIFIER id:ID)
		{	constValue = new VarRef(id.toString(), nextUniqueRegisterId++);
		}
	|	#(CONST_STRING str:STRING)
		{	constValue = new FString(str.toString(), nextUniqueRegisterId++);
		}
;

operator
	: 	AND | OR | EQ | LT | GT | NE | LTE | GTE | PLUS | MINUS | TIMES | DIVIDE
;

args returns [ArrayList<CodeAndReg> argumentList = new ArrayList<CodeAndReg>()]
{
	CodeAndReg argument;
}
	:	#(ARGUMENTS (argument=expr { argumentList.add(argument); })* )
;