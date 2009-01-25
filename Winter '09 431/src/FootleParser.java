// $ANTLR 2.7.7 (20060906): "src/footle.g" -> "FootleParser.java"$


import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class FootleParser extends antlr.LLkParser       implements FootleLexerTokenTypes
 {

protected FootleParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public FootleParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected FootleParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public FootleParser(TokenStream lexer) {
  this(lexer,1);
}

public FootleParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void program() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST program_AST = null;
		
		{
		_loop53:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				stmt();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop53;
			}
			
		} while (true);
		}
		program_AST = (AST)currentAST.root;
		returnAST = program_AST;
	}
	
	public final void stmt() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stmt_AST = null;
		Token  id = null;
		AST id_AST = null;
		AST e_AST = null;
		
		switch ( LA(1)) {
		case VAR:
		{
			AST tmp1_AST = null;
			tmp1_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp1_AST);
			match(VAR);
			AST tmp2_AST = null;
			tmp2_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp2_AST);
			match(ID);
			match(ASSIGN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(SEMI);
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case RETURN:
		{
			AST tmp5_AST = null;
			tmp5_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp5_AST);
			match(RETURN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case IF:
		{
			AST tmp6_AST = null;
			tmp6_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp6_AST);
			match(IF);
			match(LPAREN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			match(LBRACE);
			{
			_loop58:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					stmt();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop58;
				}
				
			} while (true);
			}
			match(RBRACE);
			{
			switch ( LA(1)) {
			case ELSE:
			{
				AST tmp11_AST = null;
				tmp11_AST = astFactory.create(LT(1));
				astFactory.addASTChild(currentAST, tmp11_AST);
				match(ELSE);
				match(LBRACE);
				{
				_loop61:
				do {
					if ((_tokenSet_0.member(LA(1)))) {
						stmt();
						astFactory.addASTChild(currentAST, returnAST);
					}
					else {
						break _loop61;
					}
					
				} while (true);
				}
				match(RBRACE);
				break;
			}
			case EOF:
			case VAR:
			case RETURN:
			case IF:
			case WHILE:
			case FUNCTION:
			case TRUE:
			case FALSE:
			case NEW:
			case RBRACE:
			case LPAREN:
			case NOT:
			case ID:
			case STRING:
			case INT:
			case FLOAT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case WHILE:
		{
			AST tmp14_AST = null;
			tmp14_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp14_AST);
			match(WHILE);
			match(LPAREN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			match(LBRACE);
			{
			_loop63:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					stmt();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop63;
				}
				
			} while (true);
			}
			match(RBRACE);
			stmt_AST = (AST)currentAST.root;
			break;
		}
		case FUNCTION:
		{
			AST tmp19_AST = null;
			tmp19_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp19_AST);
			match(FUNCTION);
			AST tmp20_AST = null;
			tmp20_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp20_AST);
			match(ID);
			match(LPAREN);
			paramlist();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			match(LBRACE);
			{
			_loop65:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					stmt();
					astFactory.addASTChild(currentAST, returnAST);
				}
				else {
					break _loop65;
				}
				
			} while (true);
			}
			match(RBRACE);
			stmt_AST = (AST)currentAST.root;
			break;
		}
		default:
			if ((LA(1)==ID)) {
				id = LT(1);
				id_AST = astFactory.create(id);
				astFactory.addASTChild(currentAST, id_AST);
				match(ID);
				AST tmp25_AST = null;
				tmp25_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp25_AST);
				match(ASSIGN);
				expr();
				e_AST = (AST)returnAST;
				astFactory.addASTChild(currentAST, returnAST);
				match(SEMI);
				stmt_AST = (AST)currentAST.root;
			}
			else if ((_tokenSet_1.member(LA(1)))) {
				expr();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case SEMI:
				{
					match(SEMI);
					break;
				}
				case DOT:
				{
					{
					match(DOT);
					AST tmp29_AST = null;
					tmp29_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp29_AST);
					match(ID);
					match(ASSIGN);
					expr();
					astFactory.addASTChild(currentAST, returnAST);
					match(SEMI);
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				stmt_AST = (AST)currentAST.root;
			}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = stmt_AST;
	}
	
	public final void expr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expr_AST = null;
		
		exprnr();
		astFactory.addASTChild(currentAST, returnAST);
		{
		if ((LA(1)==DOT)) {
			AST tmp32_AST = null;
			tmp32_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp32_AST);
			match(DOT);
			AST tmp33_AST = null;
			tmp33_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp33_AST);
			match(ID);
			{
			if ((LA(1)==LPAREN)) {
				match(LPAREN);
				arglist();
				astFactory.addASTChild(currentAST, returnAST);
				match(RPAREN);
			}
			else if ((_tokenSet_2.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
		}
		else if ((_tokenSet_2.member(LA(1)))) {
		}
		else {
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		}
		expr_AST = (AST)currentAST.root;
		returnAST = expr_AST;
	}
	
	public final void paramlist() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST paramlist_AST = null;
		
		paramlist_AST = (AST)currentAST.root;
		returnAST = paramlist_AST;
	}
	
	public final void exprnr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exprnr_AST = null;
		
		switch ( LA(1)) {
		case INT:
		{
			AST tmp36_AST = null;
			tmp36_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp36_AST);
			match(INT);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case FLOAT:
		{
			AST tmp37_AST = null;
			tmp37_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp37_AST);
			match(FLOAT);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case TRUE:
		{
			AST tmp38_AST = null;
			tmp38_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp38_AST);
			match(TRUE);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case FALSE:
		{
			AST tmp39_AST = null;
			tmp39_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp39_AST);
			match(FALSE);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case ID:
		{
			AST tmp40_AST = null;
			tmp40_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp40_AST);
			match(ID);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case STRING:
		{
			AST tmp41_AST = null;
			tmp41_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp41_AST);
			match(STRING);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case LPAREN:
		{
			match(LPAREN);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			{
			if ((LA(1)==LPAREN)) {
				match(LPAREN);
				arglist();
				astFactory.addASTChild(currentAST, returnAST);
				match(RPAREN);
			}
			else if ((_tokenSet_2.member(LA(1)))) {
			}
			else {
				throw new NoViableAltException(LT(1), getFilename());
			}
			
			}
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case NOT:
		{
			AST tmp46_AST = null;
			tmp46_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp46_AST);
			match(NOT);
			expr();
			astFactory.addASTChild(currentAST, returnAST);
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		case NEW:
		{
			AST tmp47_AST = null;
			tmp47_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp47_AST);
			match(NEW);
			AST tmp48_AST = null;
			tmp48_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp48_AST);
			match(ID);
			{
			match(LPAREN);
			arglist();
			astFactory.addASTChild(currentAST, returnAST);
			match(RPAREN);
			}
			exprnr_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = exprnr_AST;
	}
	
	public final void arglist() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arglist_AST = null;
		
		arglist_AST = (AST)currentAST.root;
		returnAST = arglist_AST;
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
		"PROGRAM",
		"TYPES",
		"TYPE",
		"DECLS",
		"FUNCS",
		"DECL",
		"DECLLIST",
		"PARAMS",
		"RETTYPE",
		"BLOCK",
		"STMTS",
		"INVOKE",
		"ARGS",
		"NEG"
	};
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 257700274032L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 257700273152L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 257701633906L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	
	}
