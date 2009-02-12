// $ANTLR 2.7.7 (20060906): "src/footle.g" -> "FootleTreeParser.java"$


import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;


public class FootleTreeParser extends antlr.TreeParser       implements FootleLexerTokenTypes
 {

	private int regCtr = 0; /* regCtr should ALWAYS be the next unique register # */
	
    private void error (String errormsg)
    {
        System.out.println(errormsg);
        System.exit(1);
    }
public FootleTreeParser() {
	tokenNames = _tokenNames;
}

	public final void validate(AST _t) throws RecognitionException {
		
		AST validate_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			AST __t133 = _t;
			AST tmp1_AST_in = (AST)_t;
			match(_t,PROGRAM);
			_t = _t.getFirstChild();
			stmt_list(_t);
			_t = _retTree;
			_t = __t133;
			_t = _t.getNextSibling();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void stmt_list(AST _t) throws RecognitionException {
		
		AST stmt_list_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		try {      // for error handling
			{
			_loop136:
			do {
				if (_t==null) _t=ASTNULL;
				if ((_tokenSet_0.member(_t.getType()))) {
					stmt(_t);
					_t = _retTree;
				}
				else {
					break _loop136;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final void stmt(AST _t) throws RecognitionException {
		
		AST stmt_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST cond = null;
		AST assignValue = null;
		AST constRegister = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case IFF:
			{
				AST __t138 = _t;
				AST tmp2_AST_in = (AST)_t;
				match(_t,IFF);
				_t = _t.getFirstChild();
				cond = _t==ASTNULL ? null : (AST)_t;
				expr(_t);
				_t = _retTree;
				AST __t139 = _t;
				AST tmp3_AST_in = (AST)_t;
				match(_t,THENF);
				_t = _t.getFirstChild();
				stmt_list(_t);
				_t = _retTree;
				_t = __t139;
				_t = _t.getNextSibling();
				AST __t140 = _t;
				AST tmp4_AST_in = (AST)_t;
				match(_t,ELSEF);
				_t = _t.getFirstChild();
				stmt_list(_t);
				_t = _retTree;
				_t = __t140;
				_t = _t.getNextSibling();
				_t = __t138;
				_t = _t.getNextSibling();
				break;
			}
			case ASSIGN:
			{
				AST __t141 = _t;
				AST tmp5_AST_in = (AST)_t;
				match(_t,ASSIGN);
				_t = _t.getFirstChild();
				AST tmp6_AST_in = (AST)_t;
				match(_t,ID);
				_t = _t.getNextSibling();
				assignValue = _t==ASTNULL ? null : (AST)_t;
				expr(_t);
				_t = _retTree;
				_t = __t141;
				_t = _t.getNextSibling();
				break;
			}
			case CONST_INT:
			case CONST_FLOAT:
			case CONST_BOOLEAN:
			case CONST_IDENTIFIER:
			case BINOP:
			{
				constRegister = _t==ASTNULL ? null : (AST)_t;
				expr(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	public final String  expr(AST _t) throws RecognitionException {
		String resultRegister = "uninitialized";
		
		AST expr_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST lhs = null;
		AST rhs = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case BINOP:
			{
				AST __t143 = _t;
				AST tmp7_AST_in = (AST)_t;
				match(_t,BINOP);
				_t = _t.getFirstChild();
				lhs = _t==ASTNULL ? null : (AST)_t;
				expr(_t);
				_t = _retTree;
				rhs = _t==ASTNULL ? null : (AST)_t;
				expr(_t);
				_t = _retTree;
				_t = __t143;
				_t = _t.getNextSibling();
					resultRegister = "" + regCtr++;
							System.out.println(resultRegister);
						
				break;
			}
			case CONST_INT:
			case CONST_FLOAT:
			case CONST_BOOLEAN:
			case CONST_IDENTIFIER:
			{
				const_val(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
		return resultRegister;
	}
	
	public final void const_val(AST _t) throws RecognitionException {
		
		AST const_val_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		
		try {      // for error handling
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case CONST_INT:
			{
				AST __t145 = _t;
				AST tmp8_AST_in = (AST)_t;
				match(_t,CONST_INT);
				_t = _t.getFirstChild();
				i = (AST)_t;
				match(_t,INT);
				_t = _t.getNextSibling();
				_t = __t145;
				_t = _t.getNextSibling();
				break;
			}
			case CONST_FLOAT:
			{
				AST __t146 = _t;
				AST tmp9_AST_in = (AST)_t;
				match(_t,CONST_FLOAT);
				_t = _t.getFirstChild();
				AST tmp10_AST_in = (AST)_t;
				match(_t,FLOAT);
				_t = _t.getNextSibling();
				_t = __t146;
				_t = _t.getNextSibling();
				break;
			}
			case CONST_BOOLEAN:
			{
				AST tmp11_AST_in = (AST)_t;
				match(_t,CONST_BOOLEAN);
				_t = _t.getNextSibling();
				break;
			}
			case CONST_IDENTIFIER:
			{
				AST tmp12_AST_in = (AST)_t;
				match(_t,CONST_IDENTIFIER);
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			if (_t!=null) {_t = _t.getNextSibling();}
		}
		_retTree = _t;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"var\"",
		"\"return\"",
		"\"if\"",
		"\"else\"",
		"\"while\"",
		"\"function\"",
		"\"true\"",
		"\"false\"",
		"\"new\"",
		"LBRACE",
		"RBRACE",
		"SEMI",
		"COMMA",
		"LPAREN",
		"RPAREN",
		"ASSIGN",
		"DOT",
		"NOT",
		"AND",
		"OR",
		"EQ",
		"LT",
		"GT",
		"NE",
		"LTE",
		"GTE",
		"PLUS",
		"MINUS",
		"TIMES",
		"DIVIDE",
		"ID",
		"STRING",
		"INT",
		"FLOAT",
		"FLOAT_OR_INT",
		"WS",
		"COMMENT",
		"CONST_INT",
		"CONST_FLOAT",
		"CONST_BOOLEAN",
		"CONST_IDENTIFIER",
		"IDENTIFIER",
		"BINOP",
		"IFF",
		"THENF",
		"ELSEF",
		"METHOD_CALL",
		"PROGRAM",
		"INVOKE",
		"ARGUMENTS",
		"FUNCTION_NAME",
		"FUNCTION_BODY",
		"FUNCTION_COLLECTION",
		"FIELD_LOOKUP"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 244091581890560L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	}
	
